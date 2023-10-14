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
    if (it is HttpException) {
      val err = ErrorModel(ErrorType.Api, it.code(), R.string.api_error)
      Timber.e("ApiError -> message: ${it.message}")
      Timber.e("ApiError -> code: ${it.code()}")
      Timber.e("ApiError -> cause: ${it.cause}")
      Timber.e("ApiError -> response: ${it.response()}")
      Timber.e("ApiError -> stackTrace: ${it.stackTrace}")
      invoke(err)
    }
  }
}
