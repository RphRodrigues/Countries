package br.com.rstudio.countries.arch.network

import android.content.Context
import br.com.rstudio.countries.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
    Timber.tag("OkHttp").d(message)
  }.apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

  private val okHttp: OkHttpClient by lazy {
    OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
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
