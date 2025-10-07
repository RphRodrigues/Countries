package br.com.rstudio.countries.presentation.overviewscreen.v2

import br.com.rstudio.countries.arch.prefs.AppPrefs
import br.com.rstudio.countries.data.model.CountriesHolder
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.data.repository.CountryRepository
import br.com.rstudio.countries.presentation.CountryModel.countryList
import br.com.rstudio.countries.testUtil.BaseTest
import br.com.rstudio.countries.testUtil.RxJavaSchedulerRule
import io.mockk.Ordering
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountryOverviewPresenterTest : BaseTest() {

  private val view: CountryOverviewContract.View = mockk(relaxed = true)
  private val countriesHolder: CountriesHolder = mockk(relaxed = true)
  private val tracker: CountryOverviewContract.Tracker = mockk(relaxed = true)
  private val appPrefs: AppPrefs = mockk(relaxed = true)
  private val repository: CountryRepository = mockk(relaxed = true)
  private lateinit var presenter: CountryOverviewContract.Presenter

  @get:Rule
  var testSchedulerRule = RxJavaSchedulerRule()

  @Before
  fun setUp() {
    presenter = CountryOverviewPresenter(view, appPrefs, repository, countriesHolder, tracker)
  }

  @Test
  fun `when presenter initialize with null country then shouldn't call view`() {
    presenter.onInitializer(country = null)

    verify(exactly = 0) { view.bindCountry(any(), any()) }
  }

  @Test
  fun `when presenter initialize with empty borders then should call view`() {
    every { countriesHolder.countries } returns emptyList()
    presenter.onInitializer(country = mockk())

    verify(exactly = 1) {
      view.clearViewContent()
      view.bindCountry(any(), listOf())
    }
  }

  @Test
  fun `when presenter initialize with many borders then should call view`() {
    every { countriesHolder.countries } returns countryList

    presenter.onInitializer(country = countryList.first())

    verify(exactly = 1) {
      view.clearViewContent()
      view.bindCountry(any(), listOf(countryList[2]))
    }
  }

  @Test
  fun `when presenter initializes then it should clear view contents before binding`() {
    every { countriesHolder.countries } returns countryList
    presenter.onInitializer(country = countryList.first())

    verify(ordering = Ordering.ORDERED) {
      view.clearViewContent()
      view.bindCountry(any(), listOf(countryList[2]))
    }
  }

  @Test
  fun `when onResume is called then it should track it`() {
    val country = mockk<Country>()
    presenter.onInitializer(country = country)

    presenter.onResume()

    verify {
      appPrefs.setUserNavigatedThroughApp()
      tracker.trackScreenView(country)
    }
  }

  @Test
  fun `when fetch data is called then should call repository`() {
    every { repository.getAll() } returns Observable.just(countryList)

    presenter.onFetchData("BRA")

    verify { repository.getAll() }
  }

  @Test
  fun `when repository request fails then it should show error screen`() {
    every { repository.getAll() } returns Observable.error(httpException)

    presenter.onFetchData("BRA")

    verify { view.showError(any()) }
  }

  @Test
  fun `when request fails because there's no internet then it should show error screen`() {
    every { repository.getAll() } returns Observable.error(noInternetException)

    presenter.onFetchData("BRA")

    verify { view.showError(any()) }
  }

  @Test
  fun `when repository request is successful then it should update view`() {
    val slot = slot<List<Country>>()
    every { countriesHolder.countries = capture(slot) } just Runs

    every { repository.getAll() } returns Observable.just(countryList)

    presenter.onFetchData("1")

    verify {
      view.showLoader()
      view.hideError()
      repository.getAll()
      view.hideLoader()
      view.clearViewContent()
      view.bindCountry(any(), any())
    }
    assert(slot.captured == countryList)
  }

  @Test
  fun `when repository request is successful and fetch data has wrong country code then it should show error screen`() {
    val slot = slot<List<Country>>()
    every { countriesHolder.countries = capture(slot) } just Runs

    every { repository.getAll() } returns Observable.just(countryList)

    presenter.onFetchData("blabla")

    verify {
      view.showLoader()
      view.hideError()
      repository.getAll()
      view.hideLoader()
      view.showError(any())
    }
    assert(slot.captured == countryList)
  }

  @Test
  fun `when country is clicked then should open country overview screen and track it`() {
    every { countriesHolder.countries } returns countryList
    presenter.onInitializer(countryList.first())

    presenter.onCountryClicked(country = countryList[0])

    verify {
      tracker.trackCountryClicked(countryList[0])
      view.openCountryOverviewScreen(countryList[0])
    }
  }

  @Test
  fun `when onBackPressed is called then it should finish view and track it`() {
    presenter.onBackPressed()

    verify {
      tracker.trackBackPressed()
      view.finish()
    }
  }

  @Test
  fun `when onDestroy is called then it should clean view's reference and track it`() {
    presenter.onDestroy()

    verify { tracker.trackFinish() }
    Assert.assertNull(presenter.view)
  }
}
