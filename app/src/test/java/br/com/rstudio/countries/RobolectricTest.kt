package br.com.rstudio.countries

import android.os.Build
import android.widget.TextView
import br.com.rstudio.countries.presentation.MainActivity
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class RobolectricTest {

  @Test
  fun testTextViewText() {
    val activityController = Robolectric.buildActivity(MainActivity::class.java)
    val activity = activityController.create().start().resume().visible().get()

    val textView = TextView(activity)
    textView.text = "Hello, Robolectric!"

    assertEquals("Hello, Robolectric!", textView.text.toString())
  }
}
