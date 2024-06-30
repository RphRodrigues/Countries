package br.com.rstudio.countries.presentation.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.presentation.MainActivity
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

  private val analytics: AnalyticsReport by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash_screen)

    Handler(Looper.getMainLooper())
      .postDelayed(
        {
          startActivity(MainActivity.createIntent(this))
        },
        SPLASH_SCREEN_TIMEOUT
      )
  }

  override fun onResume() {
    super.onResume()
    analytics.trackScreenView("SplashScreen")
  }

  companion object {
    private const val SPLASH_SCREEN_TIMEOUT = 2000L
  }
}
