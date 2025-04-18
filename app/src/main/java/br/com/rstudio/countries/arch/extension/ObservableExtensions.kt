package br.com.rstudio.countries.arch.extension

import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.model.ErrorModel
import br.com.rstudio.countries.arch.model.ErrorType
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException

fun <T> Observable<T>.doOnSuccess(invoke: (T) -> Unit): Disposable {
  return this.subscribe({ invoke(it) }, {})
}

fun <T> Observable<T>.doOnInternalError(invoke: (ErrorModel) -> Unit): Observable<T> {

  return this.doOnError {
    if (it is UnknownHostException) {
      val err = ErrorModel(ErrorType.Internal, -1, R.string.internal_error)
      Timber.e("InternalError -> message: ${it.message}")
      Timber.e("InternalError -> cause: ${it.cause}")
      Timber.e("InternalError -> stackTrace: ${it.stackTrace}")
      invoke(err)
    }
  }
}

fun <T> Observable<T>.doOnApiError(invoke: (ErrorModel) -> Unit): Observable<T> {

  return this.doOnError {
    val err = when (it) {
      is HttpException -> handleHttpException(it)
      else -> handleGenericException(it)
    }
    invoke(err)
  }
}

private fun handleHttpException(exception: HttpException): ErrorModel {
  Timber.tag("ApiError").e("message: ${exception.message}")
  Timber.tag("ApiError").e("code: ${exception.code()}")
  Timber.tag("ApiError").e("cause: ${exception.cause}")
  Timber.tag("ApiError").e("response: ${exception.response()}")
  Timber.tag("ApiError").e("stackTrace: ${exception.stackTrace}")

  return ErrorModel(ErrorType.Api, exception.code(), R.string.api_error)
}

private fun handleGenericException(exception: Throwable): ErrorModel {
  Timber.tag("ApiError").e("message: ${exception.message}")
  Timber.tag("ApiError").e("cause: ${exception.cause}")
  Timber.tag("ApiError").e("stackTrace: ${exception.stackTrace}")

  return ErrorModel(ErrorType.Api, -1, R.string.api_error)
}
