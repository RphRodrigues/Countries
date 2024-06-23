package br.com.rstudio.countries.testUtil

import br.com.rstudio.countries.arch.ImageLoader
import io.mockk.every
import io.mockk.mockk
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val testModule = module {

  single<Retrofit>(override = true) { (url: HttpUrl) ->
    Retrofit.Builder()
      .baseUrl(url)
      .client(OkHttpClient.Builder().build())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  single<ImageLoader>(override = true) {
    mockk(relaxed = true) {
      every { load(any(), any(), any()) } returns mockk()
    }
  }
}
