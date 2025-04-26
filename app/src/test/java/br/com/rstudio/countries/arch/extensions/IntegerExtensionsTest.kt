package br.com.rstudio.countries.arch.extensions

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rstudio.countries.arch.extension.dpToPx
import kotlin.test.Test
import kotlin.test.assertEquals
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntegerExtensionsTest {

  @Test
  fun `dpToPx should convert dp to correct px based on screen density`() {
    val context = ApplicationProvider.getApplicationContext<Context>()

    // Let's say density is 2.0 (mdpi x 2)
    val density = 2.0f
    context.resources.displayMetrics.density = density

    val dp = 10
    val expectedPx = dp * density

    val result = dp.dpToPx(context)

    assertEquals(expectedPx, result)
  }
}
