package br.com.rstudio.countries.presentation.homescreen.v2

import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.BUTTON
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.CLICK
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.COUNTRY
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.FINISH
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsParam.SCREEN_NAME
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.data.model.Country

class HomeTracker(private val analyticsReport: AnalyticsReport) : HomeContract.Tracker {

  private val screenName = "HomeFragment"

  override fun trackScreenView() {
    analyticsReport.trackScreenView(screenName)
  }

  override fun trackBackPressed() {
    analyticsReport.trackEvent(CLICK, mapOf(BUTTON to "back_button"))
  }

  override fun trackCountryClicked(country: Country) {
    analyticsReport.trackEvent(CLICK, mapOf(COUNTRY to country.name))
  }

  override fun trackFinish() {
    analyticsReport.trackEvent(FINISH, mapOf(SCREEN_NAME to screenName))
  }
}
