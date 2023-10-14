package br.com.rstudio.countries.presentation.details.screen

import br.com.rstudio.countries.data.model.Country

interface DetailsContract {

  interface View {
    fun bindCountry(country: Country, countryBorders: List<String>? = null)
  }

  interface Presenter {
    var view: View?
    fun onInitializer(country: Country?, borders: List<Country>?)
    fun onDestroy()
  }
}
