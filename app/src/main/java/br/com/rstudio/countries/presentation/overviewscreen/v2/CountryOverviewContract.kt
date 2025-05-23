package br.com.rstudio.countries.presentation.overviewscreen.v2

import br.com.rstudio.countries.data.model.Country

interface CountryOverviewContract {

  interface View {
    fun clearViewContent()
    fun bindCountry(country: Country, countryBorders: List<Country>? = null)
    fun openCountryOverviewScreen(country: Country)
    fun finish()
  }

  interface Presenter {
    var view: View?
    fun onInitializer(country: Country?)
    fun onResume()
    fun onBackPressed()
    fun onDestroy()
    fun onCountryClicked(country: Country)
  }

  interface Tracker {
    fun trackScreenView(country: Country?)
    fun trackCountryClicked(country: Country?)
    fun trackBackPressed()
    fun trackFinish()
  }
}
