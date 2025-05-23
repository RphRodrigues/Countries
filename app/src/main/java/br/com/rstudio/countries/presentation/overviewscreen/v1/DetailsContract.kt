package br.com.rstudio.countries.presentation.overviewscreen.v1

import br.com.rstudio.countries.data.model.Country

interface DetailsContract {

  interface View {
    fun clearViewContent()
    fun bindCountry(country: Country, countryBorders: List<String>? = null)
    fun finish()
  }

  interface Presenter {
    var view: View?
    fun onInitializer(country: Country?, borders: List<Country>?)
    fun onResume()
    fun onBackPressed()
    fun onDestroy()
  }

  interface Tracker {
    fun trackScreenView(country: Country?)
    fun trackBackPressed()
    fun trackFinish()
  }
}
