package br.com.rstudio.countries.presentation.listscreen

import br.com.rstudio.countries.arch.extension.doOnSuccess
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.data.repository.CountryRepository
import br.com.rstudio.countries.presentation.listscreen.model.ContinentVO
import timber.log.Timber

class ListPresenter(
  override var view: ListContract.View?,
  private val repository: CountryRepository,
  private val tracker: ListContract.Tracker
) : ListContract.Presenter {

  private var countries: List<Country>? = null
  private var continents: List<ContinentVO>? = null

  override fun onInitialize() {
    continents?.let {
      view?.bindContinents(it)
    } ?: run {
      call(repository.getAll())
        .doOnSuccess { handleData(it) }
    }
  }

  override fun onResume() {
    tracker.trackScreenView()
  }

  private fun handleData(countryList: List<Country>) {
    val continents: List<ContinentVO> = countryList
      .groupBy { it.continent }
      .filterKeys { it != "Antarctica" }
      .map { map: Map.Entry<String, List<Country>> ->
        Timber.d(map.key)
        ContinentVO(map.key, map.value, ::onCountryClickListener)
      }

    this.countries = countryList
    this.continents = continents
    view?.bindContinents(continents)
  }

  override fun onCountryClickListener(country: Country) {
    tracker.trackCountryClicked(country)

    val borders = countries?.filter { country.borders?.contains(it.code) == true }
    view?.openDetailsScreen(country, borders)
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
