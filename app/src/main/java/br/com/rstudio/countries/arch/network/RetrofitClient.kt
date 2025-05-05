package br.com.rstudio.countries.arch.network

import android.content.Context
import br.com.rstudio.countries.BuildConfig
import br.com.rstudio.countries.arch.extension.addTrustedCertForOldAndroid
import br.com.rstudio.countries.arch.util.Constants
import com.datadog.android.core.sampling.RateBasedSampler
import com.datadog.android.okhttp.DatadogEventListener
import com.datadog.android.okhttp.DatadogInterceptor
import com.datadog.android.okhttp.trace.TracingInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class RetrofitClient(private val application: Context) {

  companion object {
    private const val _1MB = 1024 * 1024L
    private const val TIMEOUT = 20L
    private const val SAMPLE_RATED = 20f
  }

  private val gson: Gson by lazy {
    GsonBuilder()
      .setPrettyPrinting()
      .setLenient()
      .create()
  }

  private fun cacheSize(): Cache {
    return Cache(application.cacheDir, maxSize = (BuildConfig.CACHE_IN_MB * _1MB))
  }

  private val loggingInterceptor = HttpLoggingInterceptor { message ->
    Timber.tag(Constants.HTTP).d(message)
  }.apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

  private val tracedHosts = listOf("restcountries.com")

  private val datadogInterceptor = DatadogInterceptor.Builder(tracedHosts)
    .setTraceSampler(RateBasedSampler(SAMPLE_RATED))
    .build()

  private val okHttp: OkHttpClient by lazy {
    OkHttpClient.Builder()
      .addTrustedCertForOldAndroid(application)
      .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
      .readTimeout(TIMEOUT, TimeUnit.SECONDS)
      .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
      // datadog interceptor must be first of the interceptors list.
      .addInterceptor(datadogInterceptor)
      .addInterceptor(loggingInterceptor)
//      .addInterceptor(CountriesInterceptor(application))
      .addNetworkInterceptor(TracingInterceptor.Builder(tracedHosts).build())
      .eventListenerFactory(DatadogEventListener.Factory())
      .cache(cacheSize())
      .build()
  }

  fun newInstance(): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .client(okHttp)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
}
