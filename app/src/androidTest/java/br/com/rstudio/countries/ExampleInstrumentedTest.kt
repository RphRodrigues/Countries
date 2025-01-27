package br.com.rstudio.countries

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import br.com.rstudio.countries.presentation.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

  private lateinit var scenario: ActivityScenario<MainActivity>

  @Before
  fun setUp() {
    scenario = ActivityScenario.launch(MainActivity::class.java)
  }

  @Test
  fun journey_test() {
    scenario.onActivity {
      onView(withId(R.id.container)).check(matches(isDisplayed()))
    }
  }

  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("br.com.rstudio.countries", appContext.packageName)
  }
}
