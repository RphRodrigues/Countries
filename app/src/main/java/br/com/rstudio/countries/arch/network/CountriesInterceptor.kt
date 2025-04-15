package br.com.rstudio.countries.arch.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

private const val SUCCESS = 200

class CountriesInterceptor(val context: Context) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val uri = chain.request().url.toUri().toString()

    return if (uri.endsWith("/all")) {
      val responseString = context.assets.open("countries_response.json")
        .bufferedReader()
        .use { it.readText() }

      chain.proceed(chain.request())
        .newBuilder()
        .code(SUCCESS)
        .protocol(Protocol.HTTP_2)
        .message("OK")
        .body(
          responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull())
        )
        .addHeader("content-type", "application/json")
        .build()
    } else {
      chain.proceed(chain.request())
    }
  }
}
