package br.com.rstudio.countries.presentation.details.screen

import br.com.rstudio.countries.data.model.Country

class DetailsPresenter(
  override var view: DetailsContract.View?,
  private val tracker: DetailsContract.Tracker
) : DetailsContract.Presenter {

  private var country: Country? = null

  override fun onInitializer(country: Country?, borders: List<Country>?) {
    if (country == null) return

    this.country = country

    val bordersList = borders?.map {
      "${it.name}   ${it.littleFlag}"
    }

    view?.bindCountry(country, bordersList)
  }

  override fun onTrackScreenView() {
    tracker.trackScreenView(country)
  }

  override fun onBackPressed() {
    tracker.trackBackPressed()
  }

  override fun onDestroy() {
    tracker.trackFinish()
    view = null
  }
}
