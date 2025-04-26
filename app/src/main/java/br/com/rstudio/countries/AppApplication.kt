package br.com.rstudio.countries

import android.app.Application
import br.com.rstudio.countries.arch.featuretoggle.RemoteConfig
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.arch.observability.crashlytics.FirebaseCrashlyticsReportTree
import br.com.rstudio.countries.arch.observability.datadog.DatadogInitializer
import br.com.rstudio.countries.di.applicationModule
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
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
    DatadogInitializer().initialize(applicationContext)

    startKoin {
      androidLogger()
      androidContext(this@AppApplication)
      modules(applicationModule)
    }

    Firebase.initialize(this)
    remoteConfig.fetchConfigs()
    analyticsReport.trackEvent(AnalyticsEvent.OPEN_APP)

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
