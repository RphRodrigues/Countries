package br.com.rstudio.countries.presentation.listscreen

import br.com.rstudio.countries.BaseTest
import br.com.rstudio.countries.RxJavaSchedulerRule
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.data.repository.CountryRepository
import br.com.rstudio.countries.presentation.CountryModel.countryList
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListPresenterTest : BaseTest() {

  private val view: ListContract.View = mockk(relaxed = true)
  private val tracker: ListContract.Tracker = mockk(relaxed = true)
  private val repository: CountryRepository = mockk(relaxed = true)

  private lateinit var presenter: ListContract.Presenter

  @get:Rule
  var testSchedulerRule = RxJavaSchedulerRule()

  @Before
  fun setup() {
    presenter = ListPresenter(view, repository, tracker)
  }

  @Test
  fun `when presenter initialize by first time then should call repository`() {
    every { repository.getAll() } returns Observable.just(countryList)

    presenter.onInitialize()

    verify { repository.getAll() }
  }

  @Test
  fun `when repository request fails then it should show error screen`() {
    every { repository.getAll() } returns Observable.error(httpException)

    presenter.onInitialize()

    verify { view.showError(any()) }
  }

  @Test
  fun `when request fails because there's no internet then it should show error screen`() {
    every { repository.getAll() } returns Observable.error(noInternetException)

    presenter.onInitialize()

    verify { view.showError(any()) }
  }

  @Test
  fun `when repository request is successful then it should update view`() {
    every { repository.getAll() } returns Observable.just(countryList)

    presenter.onInitialize()

    verify {
      view.showLoader()
      view.hideError()
      repository.getAll()
      view.hideLoader()
      view.bindContinents(any())
    }
  }

  @Test
  fun `when presenter doesn't initialize first time then should use cache`() {
    every { repository.getAll() } returns Observable.just(mockk(relaxed = true))

    presenter.onInitialize()
    presenter.onInitialize()

    verify(exactly = 1) { repository.getAll() }
    verify(exactly = 2) { view.bindContinents(any()) }
  }

  @Test
  fun `when onResume is called then it should track it`() {
    presenter.onResume()

    verify { tracker.trackScreenView() }
  }

  @Test
  fun `when country is clicked then redirect to details screen and track it`() {
    val country = mockk<Country>()

    presenter.onCountryClickListener(country)

    verify {
      view.openDetailsScreen(any(), any())
      tracker.trackCountryClicked(country)
    }
  }

  @Test
  fun `when onBackPressed is called then it should finish screen and track it`() {
    presenter.onBackPressed()

    verify {
      view.finish()
      tracker.trackBackPressed()
    }
  }

  @Test
  fun `when onDestroy is called then it should clean view's reference and track it`() {
    presenter.onDestroy()

    verify { tracker.trackFinish() }
    Assert.assertNull(presenter.view)
  }
}
