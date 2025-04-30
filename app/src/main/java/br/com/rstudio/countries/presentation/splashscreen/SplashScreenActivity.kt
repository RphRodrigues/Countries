package br.com.rstudio.countries.presentation.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.ScreenName
import br.com.rstudio.countries.arch.featuretoggle.RemoteConfig
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsParam
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.arch.util.Constants
import br.com.rstudio.countries.presentation.MainActivity
import org.koin.android.ext.android.inject
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

  private val remoteConfig: RemoteConfig by inject()
  private val analytics: AnalyticsReport by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash_screen)
    handleIntent(intent)

    remoteConfig.fetchConfigs()

    if (intent?.extras == null) {
      Handler(Looper.getMainLooper())
        .postDelayed(
          {
            startActivity(MainActivity.createIntent(this))
          },
          remoteConfig.getLong("splash_screen_timeout")
        )
    }
  }

  override fun onResume() {
    super.onResume()
    analytics.trackScreenView(ScreenName)
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    setIntent(intent)
    handleIntent(intent)
  }

  private fun handleIntent(intent: Intent?) {
    val deeplink = intent?.extras?.getString("deeplink")

    Timber.tag(Constants.DEEP_LINK).d("$ScreenName deeplink: $deeplink")

    if (!deeplink.isNullOrEmpty()) {
      analytics.trackEvent(
        AnalyticsEvent.OPENING_DEEPLINK,
        mapOf(
          AnalyticsParam.SCREEN_NAME to ScreenName,
          AnalyticsParam.DEEPLINK_URL to deeplink
        )
      )
      startActivity(MainActivity.createIntent(this, data = deeplink))
    }
  }
}
