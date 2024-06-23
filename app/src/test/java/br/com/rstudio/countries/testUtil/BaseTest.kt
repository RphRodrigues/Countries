package br.com.rstudio.countries.testUtil

import okhttp3.internal.EMPTY_RESPONSE
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

abstract class BaseTest {

  val httpException: HttpException
    get() = HttpException(Response.error<Unit>(500, EMPTY_RESPONSE))

  val noInternetException: UnknownHostException
    get() = UnknownHostException()
}
