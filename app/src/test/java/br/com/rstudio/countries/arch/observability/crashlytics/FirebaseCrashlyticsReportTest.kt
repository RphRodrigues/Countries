package br.com.rstudio.countries.arch.observability.crashlytics

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirebaseCrashlyticsReportTest {

  private val firebaseCrashlytics = mockk<FirebaseCrashlytics>(relaxed = true)
  private lateinit var crashlyticsReport: FirebaseCrashlyticsReport

  @Before
  fun setUp() {
    crashlyticsReport = FirebaseCrashlyticsReport(firebaseCrashlytics)
  }

  @Test
  fun `verify custom keys are set on init`() {
    verify {
      firebaseCrashlytics.setCustomKey("is_emulator", true)
      firebaseCrashlytics.setCustomKey("country", "US")
      firebaseCrashlytics.setCustomKey("language", "en-US")
    }
  }

  @Test
  fun `when send exception is called then it should send exception to firebase crashlytics`() {
    val exception = Exception("exception mocked")

    crashlyticsReport.sendException(exception)

    verify { firebaseCrashlytics.recordException(exception) }
  }
}
