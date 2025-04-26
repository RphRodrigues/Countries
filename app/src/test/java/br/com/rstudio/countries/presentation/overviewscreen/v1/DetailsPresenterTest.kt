package br.com.rstudio.countries.presentation.overviewscreen.v1

import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.CountryModel.countryList
import io.mockk.Ordering
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DetailsPresenterTest {

  private val view: DetailsContract.View = mockk(relaxed = true)
  private val tracker: DetailsContract.Tracker = mockk(relaxed = true)
  private lateinit var presenter: DetailsContract.Presenter

  @Before
  fun setUp() {
    presenter = DetailsPresenter(view, tracker)
  }

  @Test
  fun `when presenter initialize with null country then shouldn't call view`() {
    presenter.onInitializer(country = null, borders = null)

    verify(exactly = 0) { view.bindCountry(any(), any()) }
  }

  @Test
  fun `when presenter initialize with null borders then should call view`() {
    presenter.onInitializer(country = mockk(), borders = null)

    verify(exactly = 1) {
      view.clearViewContent()
      view.bindCountry(any(), null)
    }
  }

  @Test
  fun `when presenter initialize with many borders then should call view`() {
    presenter.onInitializer(country = mockk(), borders = countryList)

    verify(exactly = 1) {
      view.clearViewContent()
      view.bindCountry(
        any(),
        listOf("Country 1   litter flag 1", "Country 2   litter flag 2", "Country 3   litter flag 3")
      )
    }
  }

  @Test
  fun `when presenter initializes then it should clear view contents before binding`() {
    presenter.onInitializer(country = mockk(), borders = null)

    verify(ordering = Ordering.ORDERED) {
      view.clearViewContent()
      view.bindCountry(any(), null)
    }
  }

  @Test
  fun `when onResume is called then it should track it`() {
    val country = mockk<Country>()
    presenter.onInitializer(country = country, borders = null)

    presenter.onResume()

    verify {
      tracker.trackScreenView(country)
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
