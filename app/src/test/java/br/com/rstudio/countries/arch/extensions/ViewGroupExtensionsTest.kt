package br.com.rstudio.countries.arch.extensions

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rstudio.countries.arch.extension.updateMarginLayoutParams
import junit.framework.TestCase.assertEquals
import kotlin.test.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewGroupExtensionsTest {

  @Test
  fun `updateMarginLayoutParams should modify margins`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val view = FrameLayout(context)

    val layoutParams = ViewGroup.MarginLayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
      leftMargin = 0
      topMargin = 0
    }

    view.layoutParams = layoutParams

    // Act
    view.updateMarginLayoutParams {
      leftMargin = 24
      topMargin = 12
    }

    // Assert
    val updatedParams = view.layoutParams as ViewGroup.MarginLayoutParams
    assertEquals(24, updatedParams.leftMargin)
    assertEquals(12, updatedParams.topMargin)
  }
}
