package br.com.rstudio.countries.presentation.overviewscreen.v2

import br.com.rstudio.countries.data.model.CountriesHolder
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.CountryModel.countryList
import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CountryOverviewPresenterTest {

  private val view: CountryOverviewContract.View = mockk(relaxed = true)
  private val countriesHolder: CountriesHolder = mockk(relaxed = true)
  private val tracker: CountryOverviewContract.Tracker = mockk(relaxed = true)
  private lateinit var presenter: CountryOverviewContract.Presenter

  @Before
  fun setUp() {
    presenter = CountryOverviewPresenter(view, countriesHolder, tracker)
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
      tracker.trackScreenView(country)
    }
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
