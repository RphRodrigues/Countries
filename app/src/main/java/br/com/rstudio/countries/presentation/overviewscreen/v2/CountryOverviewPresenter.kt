package br.com.rstudio.countries.presentation.overviewscreen.v2

import br.com.rstudio.countries.data.model.Country

class CountryOverviewPresenter(
  override var view: CountryOverviewContract.View?,
  private val tracker: CountryOverviewContract.Tracker
) : CountryOverviewContract.Presenter {

  private var country: Country? = null
  private var bordersCountries: List<Country>? = null

  override fun onInitializer(country: Country?, borders: List<Country>?) {
    if (country == null) return

    this.country = country
    this.bordersCountries = borders

    view?.clearViewContent()
    view?.bindCountry(country, borders)
  }

  override fun onResume() {
    tracker.trackScreenView(country)
  }

  override fun onCountryClicked(country: Country) {
    tracker.trackCountryClicked(country)

    val borders = bordersCountries?.filter { country.borders?.contains(it.code) == true }
    view?.openCountryOverviewScreen(country, borders)
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
