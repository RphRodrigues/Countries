package br.com.rstudio.countries.arch.network

import br.com.rstudio.countries.arch.extension.doOnApiError
import br.com.rstudio.countries.arch.extension.doOnInternalError
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface NetworkPresenter<V : NetworkView> {

  var view: V?

  fun <T> call(observable: Observable<T>): Observable<T> {
    view?.showLoader()
    view?.hideError()

    return observable
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
//      .delaySubscription(1500L, TimeUnit.MILLISECONDS)
      .doOnInternalError { view?.showError(it) }
      .doOnApiError { view?.showError(it) }
      .doOnTerminate { view?.hideLoader() }
  }
}
