package br.com.rstudio.countries.presentation.homescreen.v2

import br.com.rstudio.countries.arch.observability.analytics.FirebaseAnalyticsReport
import br.com.rstudio.countries.data.model.Country
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class HomeTrackerTest {

  private lateinit var tracker: HomeContract.Tracker
  private val analytics = mockk<FirebaseAnalyticsReport>(relaxed = true)

  @Before
  fun setup() {
    tracker = HomeTracker(analytics)
  }

  @Test
  fun `when track screen view is called then it should track it`() {
    tracker.trackScreenView()

    verify { analytics.trackScreenView("HomeFragment") }
  }

  @Test
  fun `when track back pressed is called then it should track it`() {
    tracker.trackBackPressed()

    verify {
      analytics.trackEvent("click", mapOf("button" to "back_button"))
    }
  }

  @Test
  fun `when track country clicked is called then it should track it`() {
    val country = mockk<Country> {
      every { name } returns "Brazil"
    }

    tracker.trackCountryClicked(country)

    verify {
      analytics.trackEvent("click", mapOf("country" to "Brazil"))
    }
  }

  @Test
  fun `when track finish is called then it should track it`() {
    tracker.trackFinish()

    verify {
      analytics.trackEvent("finish", mapOf("screen_name" to "HomeFragment"))
    }
  }
}
