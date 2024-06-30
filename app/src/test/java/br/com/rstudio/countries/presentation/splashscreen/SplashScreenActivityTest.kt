package br.com.rstudio.countries.presentation.splashscreen

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.arch.observability.analytics.FirebaseAnalyticsReport
import br.com.rstudio.countries.testUtil.TestApplication
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class SplashScreenActivityTest : KoinTest {

  private val analytics = mockk<FirebaseAnalyticsReport>(relaxed = true)
  private lateinit var activity: ActivityScenario<SplashScreenActivity>

  private val module = module {
    single<AnalyticsReport>(override = true) { analytics }
  }

  @Before
  fun setUp() {
    getKoin().loadModules(listOf(module))
    activity = ActivityScenario.launch(SplashScreenActivity::class.java)
  }

  @After
  fun tearDown() {
    getKoin().unloadModules(listOf(module))
    activity.close()
  }

  @Test
  fun `when splash screen is opened it should show splash screen content`() {
    activity.onActivity {
      onView(withId(R.id.splash_screen_content)).check(matches(isDisplayed()))
    }
  }

  @Test
  fun `when splash screen is opened it should tracker it`() {

    verify(exactly = 1) {
      analytics.trackScreenView("SplashScreen")
    }
  }
}
