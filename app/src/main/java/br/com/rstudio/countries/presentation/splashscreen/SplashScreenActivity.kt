package br.com.rstudio.countries.presentation.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.featuretoggle.RemoteConfig
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.presentation.MainActivity
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

  private val remoteConfig: RemoteConfig by inject()
  private val analytics: AnalyticsReport by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash_screen)

    Handler(Looper.getMainLooper())
      .postDelayed(
        {
          startActivity(MainActivity.createIntent(this))
        },
        remoteConfig.getLong("splash_screen_timeout")
      )
  }

  override fun onResume() {
    super.onResume()
    analytics.trackScreenView("SplashScreen")
  }
}
