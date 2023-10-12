package br.com.rstudio.countries.presentation.details.screen

import br.com.rstudio.countries.data.model.Country

class DetailsPresenter(
  override var view: DetailsContract.View?
) : DetailsContract.Presenter {

  override fun onInitializer(country: Country?, borders: List<Country>?) {
    if (country == null) return

    val bordersList = borders?.map {
      "${it.name}   ${it.littleFlag}"
    }

    view?.bindCountry(country, bordersList)
  }

  override fun onDestroy() {
    view = null
  }
}
