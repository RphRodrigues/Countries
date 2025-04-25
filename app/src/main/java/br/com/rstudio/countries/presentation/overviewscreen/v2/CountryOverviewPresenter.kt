package br.com.rstudio.countries.presentation.overviewscreen.v2

import br.com.rstudio.countries.data.model.CountriesHolder
import br.com.rstudio.countries.data.model.Country

class CountryOverviewPresenter(
  override var view: CountryOverviewContract.View?,
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

  override fun onResume() {
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
