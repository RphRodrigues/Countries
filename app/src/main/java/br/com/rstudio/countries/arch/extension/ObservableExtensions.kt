package br.com.rstudio.countries.arch.extension

import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.model.ErrorModel
import br.com.rstudio.countries.arch.model.ErrorType
import br.com.rstudio.countries.arch.util.Constants
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException

fun <T> Observable<T>.subscribeObserveDefault(): Observable<T> {
  return this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.doOnSuccess(invoke: (T) -> Unit): Disposable {
  return this.subscribe({ invoke(it) }, {})
}

fun <T> Single<T>.subscribeObserveDefault(): Single<T> {
  return this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.doOnSuccessful(invoke: (T) -> Unit): Disposable {
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
  Timber.tag(Constants.HTTP_ERROR).e("message: ${exception.message}")
  Timber.tag(Constants.HTTP_ERROR).e("code: ${exception.code()}")
  Timber.tag(Constants.HTTP_ERROR).e("cause: ${exception.cause}")
  Timber.tag(Constants.HTTP_ERROR).e("response: ${exception.response()}")
  Timber.tag(Constants.HTTP_ERROR).e("stackTrace: ${exception.stackTrace}")

  return ErrorModel(ErrorType.Api, exception.code(), R.string.api_error)
}

private fun handleGenericException(exception: Throwable): ErrorModel {
  Timber.tag(Constants.HTTP_ERROR).e("message: ${exception.message}")
  Timber.tag(Constants.HTTP_ERROR).e("cause: ${exception.cause}")
  Timber.tag(Constants.HTTP_ERROR).e("stackTrace: ${exception.stackTrace}")

  return ErrorModel(ErrorType.Api, -1, R.string.api_error)
}
