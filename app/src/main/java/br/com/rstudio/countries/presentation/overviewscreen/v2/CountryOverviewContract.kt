package br.com.rstudio.countries.presentation.overviewscreen.v2

import br.com.rstudio.countries.arch.network.NetworkPresenter
import br.com.rstudio.countries.arch.network.NetworkView
import br.com.rstudio.countries.data.model.Country

interface CountryOverviewContract {

  interface View : NetworkView {
    fun clearViewContent()
    fun bindCountry(country: Country, countryBorders: List<Country>? = null)
    fun openCountryOverviewScreen(country: Country)
    fun finish()
  }

  interface Presenter : NetworkPresenter<View> {
    override var view: View?
    fun onInitializer(country: Country?)
    fun onResume()
    fun onBackPressed()
    fun onDestroy()
    fun onCountryClicked(country: Country)
    fun onFetchData(code: String?)
  }

  interface Tracker {
    fun trackScreenView(country: Country?)
    fun trackCountryClicked(country: Country?)
    fun trackBackPressed()
    fun trackFinish()
  }
}
