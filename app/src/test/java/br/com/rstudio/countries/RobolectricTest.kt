package br.com.rstudio.countries

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rstudio.countries.presentation.MainActivity
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric

@RunWith(AndroidJUnit4::class)
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
