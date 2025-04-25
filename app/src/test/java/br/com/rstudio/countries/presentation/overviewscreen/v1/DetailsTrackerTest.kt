package br.com.rstudio.countries.presentation.overviewscreen.v1

import br.com.rstudio.countries.arch.observability.analytics.FirebaseAnalyticsReport
import br.com.rstudio.countries.data.model.Country
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class DetailsTrackerTest {

  private lateinit var tracker: DetailsContract.Tracker
  private val analytics = mockk<FirebaseAnalyticsReport>(relaxed = true)

  private val country = mockk<Country> {
    every { name } returns "Portugal"
  }

  @Before
  fun setup() {
    tracker = DetailsTracker(analytics)
  }

  @Test
  fun `when track screen view is called then it should track it`() {
    tracker.trackScreenView(country)

    verify {
      analytics.trackScreenView("DetailsFragment")
      analytics.trackEvent("item_viewed", mapOf("country" to "Portugal"))
    }
  }

  @Test
  fun `when track back pressed is called then it should track it`() {
    tracker.trackBackPressed()

    verify {
      analytics.trackEvent("click", mapOf("button" to "back_button"))
    }
  }

  @Test
  fun `when track finish is called then it should track it`() {
    tracker.trackFinish()

    verify {
      analytics.trackEvent("finish", mapOf("screen_name" to "DetailsFragment"))
    }
  }
}
