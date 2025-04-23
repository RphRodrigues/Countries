package br.com.rstudio.countries.presentation.homescreen.v1

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rstudio.countries.R
import br.com.rstudio.countries.testUtil.HttpIdlingResourceRule
import br.com.rstudio.countries.testUtil.MockServer
import br.com.rstudio.countries.testUtil.RxJavaSchedulerRule
import br.com.rstudio.countries.testUtil.TestApplication
import br.com.rstudio.countries.testUtil.UiVerify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class ListFragmentTest {

  private val mockServer = MockServer()
  private lateinit var fragment: FragmentScenario<ListFragment>

  @get:Rule
  var testSchedulerRule = RxJavaSchedulerRule()

  @get:Rule
  var httpIdlingResourceRule = HttpIdlingResourceRule()

  @Before
  fun setup() {
    mockServer.start()
  }

  @After
  fun tearDown() {
    mockServer.stop()
  }

  @Test
  fun `given list fragment is initiated with success then check if the countries are displayed`() {
    mockServer.responseSuccess("rest-countries-response.json")

    fragment = launchFragmentInContainer<ListFragment>()

    fragment.onFragment {
      UiVerify.checkViewIsDisplayed("Oceania")
      UiVerify.checkViewIsDisplayed("Wallis and Futuna")
      UiVerify.checkViewIsDisplayed("Australia")
      UiVerify.checkViewIsDisplayed("Vanuatu")

      UiVerify.checkViewIsDisplayed("Europe")
      UiVerify.checkViewIsDisplayed("Iceland")
      UiVerify.checkViewIsDisplayed("Luxembourg")
      UiVerify.checkViewIsDisplayed("Estonia")

      UiVerify.scrollToPosition(R.id.main_recycler_view, 4)

      UiVerify.checkViewIsDisplayed("Africa")
      UiVerify.checkViewIsDisplayed("Mali")
      UiVerify.checkViewIsDisplayed("Comoros")
      UiVerify.checkViewIsDisplayed("Gambia")

      UiVerify.checkViewIsDisplayed("North America")
      UiVerify.checkViewIsDisplayed("Canada")
      UiVerify.checkViewIsDisplayed("United States Virgin Islands")

      UiVerify.scrollToPosition(R.id.main_recycler_view, 6)

      UiVerify.checkViewIsDisplayed("South America")
      UiVerify.checkViewIsDisplayed("Guyana")
      UiVerify.checkViewIsDisplayed("Uruguay")
      UiVerify.checkViewIsDisplayed("Chile")

      UiVerify.checkViewIsDisplayed("Asia")
      UiVerify.checkViewIsDisplayed("Cambodia")
      UiVerify.checkViewIsDisplayed("South Korea")
      UiVerify.checkViewIsDisplayed("Maldives")
    }
  }
}
