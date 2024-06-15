package br.com.rstudio.countries

import android.app.Application
import br.com.rstudio.countries.arch.observability.crashlytics.FirebaseCrashlyticsReportTree
import br.com.rstudio.countries.di.ApplicationModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import timber.log.Timber

class AppApplication : Application() {

  private val firebaseCrashlyticsReportTree: FirebaseCrashlyticsReportTree by inject()

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger()
      androidContext(this@AppApplication)
      modules(ApplicationModule)
    }

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    } else {
      Timber.plant(firebaseCrashlyticsReportTree)
    }
  }

  override fun onTerminate() {
    super.onTerminate()
    stopKoin()
  }
}
