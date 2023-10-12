package br.com.rstudio.countries.presentation.details.screen

import br.com.rstudio.countries.presentation.CountryModel.countryList
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DetailsPresenterTest {

  private val view: DetailsContract.View = mockk(relaxed = true)
  private lateinit var presenter: DetailsContract.Presenter

  @Before
  fun setUp() {
    presenter = DetailsPresenter(view)
  }

  @Test
  fun `when presenter initialize with null country then shouldn't call view`() {
    presenter.onInitializer(country = null, borders = null)

    verify(exactly = 0) { view.bindCountry(any(), any()) }
  }

  @Test
  fun `when presenter initialize with null borders then should call view`() {
    presenter.onInitializer(country = mockk(), borders = null)

    verify(exactly = 1) { view.bindCountry(any(), null) }
  }

  @Test
  fun `when presenter initialize with many borders then should call view`() {
    presenter.onInitializer(country = mockk(), borders = countryList)

    verify(exactly = 1) {
      view.bindCountry(
        any(),
        listOf("Country 1   litter flag 1", "Country 2   litter flag 2", "Country 3   litter flag 3")
      )
    }
  }

  @Test
  fun `when onDestroy is called then it should clean view's reference`() {
    presenter.onDestroy()

    Assert.assertNull(presenter.view)
  }
}
