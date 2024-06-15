package br.com.rstudio.countries.presentation.listscreen

import br.com.rstudio.countries.arch.network.NetworkPresenter
import br.com.rstudio.countries.arch.network.NetworkView
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.listscreen.model.ContinentVO

interface ListContract {

  interface View : NetworkView {
    fun bindContinents(continentList: List<ContinentVO>)
    fun openDetailsScreen(country: Country, borders: List<Country>?)
    fun finish()
  }

  interface Presenter : NetworkPresenter<View> {
    fun onInitialize()
    fun onCountryClickListener(country: Country)
    fun onBackPressed()
    fun onDestroy()
    fun onTrackScreenView()
  }

  interface Tracker {
    fun trackScreenView()
    fun trackBackPressed()
    fun trackCountryClicked(country: Country)
    fun trackFinish()
  }
}
