package br.com.rstudio.countries

import android.app.Application
import br.com.rstudio.countries.arch.featuretoggle.RemoteConfig
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.arch.observability.crashlytics.FirebaseCrashlyticsReportTree
import br.com.rstudio.countries.arch.observability.datadog.DatadogInitializer
import br.com.rstudio.countries.arch.util.Constants
import br.com.rstudio.countries.di.applicationModule
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import timber.log.Timber

class AppApplication : Application() {

  private val remoteConfig: RemoteConfig by inject()
  private val analyticsReport: AnalyticsReport by lazy { getKoin().get() }
  private val firebaseCrashlyticsReportTree: FirebaseCrashlyticsReportTree by inject()

  override fun onCreate() {
    super.onCreate()
    DatadogInitializer().run(applicationContext)

    startKoin {
      androidLogger()
      androidContext(this@AppApplication)
      modules(applicationModule)
    }

    Firebase.initialize(this)
    remoteConfig.fetchConfigs()
    analyticsReport.trackEvent(AnalyticsEvent.OPEN_APP)

    FirebaseMessaging.getInstance()
      .token
      .addOnCompleteListener { task ->
        if (!task.isSuccessful) {
          Timber.tag(Constants.NOTIFICATION).w("Fetching FCM registration token failed: ${task.exception}")
          return@addOnCompleteListener
        }

        Timber.tag(Constants.NOTIFICATION).d("new token: ${task.result}")
      }

    FirebaseMessaging.getInstance()
      .subscribeToTopic(getString(R.string.firebase_topic))
      .addOnCompleteListener { task ->
        if (!task.isSuccessful) {
          Timber.tag(Constants.NOTIFICATION).w("Subscribe to topic failed: ${task.exception}")
          return@addOnCompleteListener
        }
      }

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    } else {
      Timber.plant(firebaseCrashlyticsReportTree)
    }
  }

  override fun onTerminate() {
    super.onTerminate()

    analyticsReport.trackEvent(AnalyticsEvent.CLOSE_APP)

    stopKoin()
  }
}
