package br.com.rstudio.countries.presentation.overviewscreen.v2

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rstudio.countries.R
import br.com.rstudio.countries.presentation.CountryModel.countryBorders
import br.com.rstudio.countries.presentation.CountryModel.countryDetail
import br.com.rstudio.countries.testUtil.UiVerify
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryOverviewFragmentTest {

  private lateinit var fragment: FragmentScenario<CountryOverviewFragment>

  @Test
  fun `given country overview fragment is initiated with country without borders then check if all views`() {
    val bundle = bundleOf("country" to countryDetail)

    fragment = launchFragmentInContainer<CountryOverviewFragment>(fragmentArgs = bundle)

    fragment.onFragment {
      UiVerify.checkViewIsDisplayed(R.id.fragment_flag_image_view)
      UiVerify.checkViewIsDisplayed("Brazil")
      UiVerify.checkViewIsDisplayed("Population: 212,559,409")
      UiVerify.checkViewIsDisplayed("Capital: Brasília")
      UiVerify.checkViewIsDisplayed("Continent: South America")
    }
  }

  @Test
  @Ignore
  fun `given details fragment is initiated then check if all views are displayed`() {
    val bundle = bundleOf("country" to countryDetail, "borders" to countryBorders)

    fragment = launchFragmentInContainer<CountryOverviewFragment>(fragmentArgs = bundle)

    fragment.onFragment {
      UiVerify.checkViewIsDisplayed(R.id.fragment_flag_image_view)
      UiVerify.checkViewIsDisplayed("Brazil")
      UiVerify.checkViewIsDisplayed("Population: 212,559,409")
      UiVerify.checkViewIsDisplayed("Capital: Brasília")
      UiVerify.checkViewIsDisplayed("Continent: South America")
      UiVerify.checkViewIsDisplayed("Number of bordering countries: 3")
      UiVerify.checkViewIsDisplayed("Countries that share a border")
      UiVerify.checkViewIsDisplayed("Argentina")
      UiVerify.checkViewIsDisplayed("Uruguay")
      UiVerify.checkViewIsDisplayed("Colombia")
    }
  }
}
