package br.com.rstudio.countries.presentation.homescreen.v2

import br.com.rstudio.countries.arch.network.NetworkPresenter
import br.com.rstudio.countries.arch.network.NetworkView
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.homescreen.v1.model.ContinentVO

interface HomeContract {

  interface View : NetworkView {
    fun bindData(mostPopularCountries: List<Country>, continentList: List<ContinentVO>)
    fun openDetailsScreen(country: Country, borders: List<Country>?)
    fun openCountryOverviewScreen(country: Country, borders: List<Country>?)
    fun finish()
  }

  interface Presenter : NetworkPresenter<View> {
    fun onInitialize()
    fun onResume()
    fun onCountryClickListener(country: Country)
    fun onBackPressed()
    fun onDestroy()
  }

  interface Tracker {
    fun trackScreenView()
    fun trackBackPressed()
    fun trackCountryClicked(country: Country)
    fun trackFinish()
  }
}
