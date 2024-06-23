package br.com.rstudio.countries.arch.observability.analytics

import android.os.Bundle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.analytics.FirebaseAnalytics
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirebaseAnalyticsReportTest {

  private lateinit var analyticsReport: AnalyticsReport
  private val firebaseAnalytics = mockk<FirebaseAnalytics>(relaxed = true)

  @Before
  fun setUp() {
    analyticsReport = FirebaseAnalyticsReport(firebaseAnalytics)
  }

  @Test
  fun `verify user properties are set on init`() {

    verify {
      firebaseAnalytics.setUserProperty(UserProperty.MANUFACTURER, "unknown")
      firebaseAnalytics.setUserProperty(UserProperty.MODEL, "robolectric")
      firebaseAnalytics.setUserProperty(UserProperty.BRAND, "Android")
      firebaseAnalytics.setUserProperty(UserProperty.COUNTRY, "US")
      firebaseAnalytics.setUserProperty(UserProperty.LANGUAGE, "en-US")
      firebaseAnalytics.setUserProperty(UserProperty.SYSTEM_VERSION, "9")
      firebaseAnalytics.setUserProperty(UserProperty.SDK_VERSION, "28")
      firebaseAnalytics.setUserProperty(UserProperty.APP_VERSION, any())
    }
  }

  @Test
  fun `when log screen view is called then it should tracker the screen name`() {
    val screen = "HomeActivity"
    val bundleSlot = slot<Bundle>()
    every { firebaseAnalytics.logEvent(any(), capture(bundleSlot)) } answers { }

    analyticsReport.trackScreenView(screen)

    verify { firebaseAnalytics.logEvent("screen_view", capture(bundleSlot)) }
    assertEquals(screen, bundleSlot.captured.getString("screen_name"))
  }

  @Test
  fun `when log event is called then it should tracker the event`() {
    val bundleSlot = slot<Bundle>()

    analyticsReport.trackEvent("click", mapOf("country" to "Italian"))

    verify { firebaseAnalytics.logEvent("click", capture(bundleSlot)) }
    assertEquals("Italian", bundleSlot.captured.getString("country"))
  }
}
