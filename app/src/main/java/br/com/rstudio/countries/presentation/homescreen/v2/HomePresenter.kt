package br.com.rstudio.countries.presentation.homescreen.v2

import br.com.rstudio.countries.arch.extension.doOnSuccess
import br.com.rstudio.countries.arch.featuretoggle.FeatureToggles
import br.com.rstudio.countries.arch.featuretoggle.RemoteConfig
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.data.repository.CountryRepository
import br.com.rstudio.countries.presentation.homescreen.v1.model.ContinentVO
import timber.log.Timber

class HomePresenter(
  override var view: HomeContract.View?,
  private val repository: CountryRepository,
  private val tracker: HomeContract.Tracker,
  private val remoteConfig: RemoteConfig
) : HomeContract.Presenter {

  private var countries: List<Country>? = null
  private var continents: List<ContinentVO>? = null
  private lateinit var mostPopularCountries: List<Country>

  override fun onInitialize() {
    continents?.let {
      view?.bindData(mostPopularCountries, it)
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

    if (!::mostPopularCountries.isInitialized) {
      mostPopularCountries = countryList.filter { it.population > TWO_HUNDRED_MILLIONS }.shuffled()
    }

    view?.bindData(mostPopularCountries, continents)
  }

  override fun onCountryClickListener(country: Country) {
    tracker.trackCountryClicked(country)

    val borders = countries?.filter { country.borders?.contains(it.code) == true }

    if (remoteConfig.getBoolean(FeatureToggles.SHOW_COUNTRY_OVERVIEW_V2)) {
      view?.openCountryOverviewScreen(country, borders)
    } else {
      view?.openDetailsScreen(country, borders)
    }
  }

  override fun onBackPressed() {
    tracker.trackBackPressed()
    view?.finish()
  }

  override fun onDestroy() {
    tracker.trackFinish()
    view = null
  }

  companion object {
    const val TWO_HUNDRED_MILLIONS = 200000000
  }
}
