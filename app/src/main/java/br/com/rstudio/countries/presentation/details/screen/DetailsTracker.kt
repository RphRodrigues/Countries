package br.com.rstudio.countries.presentation.details.screen

import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.BUTTON
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.CLICK
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.COUNTRY
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.FINISH
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.ITEM_VIEWED
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsParam.SCREEN_NAME
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.data.model.Country

class DetailsTracker(private val analyticsReport: AnalyticsReport) : DetailsContract.Tracker {

  private val screenName = "DetailsFragment"

  override fun trackScreenView(country: Country?) {
    analyticsReport.trackScreenView(screenName)
    analyticsReport.trackEvent(ITEM_VIEWED, mapOf(COUNTRY to country?.name))
  }

  override fun trackBackPressed() {
    analyticsReport.trackEvent(CLICK, mapOf(BUTTON to "back_button"))
  }

  override fun trackFinish() {
    analyticsReport.trackEvent(FINISH, mapOf(SCREEN_NAME to screenName))
  }
}
