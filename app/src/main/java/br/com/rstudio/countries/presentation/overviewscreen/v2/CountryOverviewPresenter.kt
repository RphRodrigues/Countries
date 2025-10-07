package br.com.rstudio.countries.presentation.overviewscreen.v2

import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.doOnSuccess
import br.com.rstudio.countries.arch.model.ErrorModel
import br.com.rstudio.countries.arch.model.ErrorType
import br.com.rstudio.countries.arch.prefs.AppPrefs
import br.com.rstudio.countries.arch.util.Constants
import br.com.rstudio.countries.data.model.CountriesHolder
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.data.repository.CountryRepository
import timber.log.Timber

class CountryOverviewPresenter(
  override var view: CountryOverviewContract.View?,
  private val appPrefs: AppPrefs,
  private val repository: CountryRepository,
  private val countriesHolder: CountriesHolder,
  private val tracker: CountryOverviewContract.Tracker
) : CountryOverviewContract.Presenter {

  private var country: Country? = null

  private fun getBorders(country: Country): List<Country>? =
    countriesHolder.countries?.filter {
      country.borders?.contains(it.code) == true
    }

  override fun onInitializer(country: Country?) {
    if (country == null) return

    this.country = country

    view?.clearViewContent()
    view?.bindCountry(country, getBorders(country))
  }

  override fun onFetchData(code: String?) {
    call(repository.getAll())
      .doOnSuccess { countries ->
        countriesHolder.countries = countries

        val country = countries.firstOrNull { it.code == code }

        if (country == null) {
          Timber.tag(Constants.DEEP_LINK).w("Country passed in deeplink not found")

          view?.showError(
            ErrorModel(ErrorType.DeepLink, ErrorType.DeepLink.CODE, R.string.error)
          )
          return@doOnSuccess
        }

        view?.clearViewContent()
        view?.bindCountry(country, getBorders(country))
      }
  }

  override fun onResume() {
    appPrefs.setUserNavigatedThroughApp()
    tracker.trackScreenView(country)
  }

  override fun onCountryClicked(country: Country) {
    tracker.trackCountryClicked(country)
    view?.openCountryOverviewScreen(country)
  }

  override fun onBackPressed() {
    tracker.trackBackPressed()
    view?.finish()
  }

  override fun onDestroy() {
    tracker.trackFinish()
    view = null
  }
}
