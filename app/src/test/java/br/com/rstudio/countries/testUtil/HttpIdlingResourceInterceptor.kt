package br.com.rstudio.countries.testUtil

import okhttp3.Interceptor
import okhttp3.Response

class HttpIdlingResourceInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    RetrofitIdlingResource.increment()

    return try {
      chain.proceed(chain.request())
    } finally {
      RetrofitIdlingResource.decrement()
    }
  }
}
