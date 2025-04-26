package br.com.rstudio.countries.presentation.overviewscreen.v1

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rstudio.countries.R
import br.com.rstudio.countries.presentation.CountryModel.countryBorders
import br.com.rstudio.countries.presentation.CountryModel.countryDetail
import br.com.rstudio.countries.testUtil.UiVerify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

  private lateinit var fragment: FragmentScenario<DetailsFragment>

  @Test
  fun `given details fragment is initiated with country without borders then check if all views`() {
    val bundle = bundleOf("country" to countryDetail)

    fragment = launchFragmentInContainer<DetailsFragment>(fragmentArgs = bundle)

    fragment.onFragment {
      UiVerify.checkViewIsDisplayed(R.id.details_image_view)
      UiVerify.checkViewByTagIsDisplayed("flag_icon")
      UiVerify.checkViewIsDisplayed("Brazil")
      UiVerify.checkViewByTagIsDisplayed("population_icon")
      UiVerify.checkViewIsDisplayed("212,559,409")
      UiVerify.checkViewByTagIsDisplayed("capital_icon")
      UiVerify.checkViewIsDisplayed("Capital")
      UiVerify.checkViewIsDisplayed("Brasília")
    }
  }

  @Test
  fun `given details fragment is initiated then check if all views are displayed`() {
    val bundle = Bundle().apply {
      putParcelable("country", countryDetail)
      putParcelableArrayList("borders", countryBorders)
    }

    fragment = launchFragmentInContainer<DetailsFragment>(fragmentArgs = bundle)

    fragment.onFragment {
      UiVerify.checkViewIsDisplayed(R.id.details_image_view)
      UiVerify.checkViewByTagIsDisplayed("flag_icon")
      UiVerify.checkViewIsDisplayed("Brazil")
      UiVerify.checkViewByTagIsDisplayed("population_icon")
      UiVerify.checkViewIsDisplayed("212,559,409")
      UiVerify.checkViewByTagIsDisplayed("capital_icon")
      UiVerify.checkViewIsDisplayed("Capital")
      UiVerify.checkViewIsDisplayed("Brasília")
      UiVerify.checkViewByTagIsDisplayed("border_icon")
      UiVerify.checkViewIsDisplayed("Border")
      UiVerify.checkViewIsDisplayed("Argentina   🇦🇷")
      UiVerify.checkViewIsDisplayed("Uruguay   🇺🇾")
      UiVerify.checkViewIsDisplayed("Colombia   🇨🇴")
    }
  }
}
