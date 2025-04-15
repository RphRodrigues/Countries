package br.com.rstudio.countries.testUtil

import android.app.Application
import br.com.rstudio.countries.R
import br.com.rstudio.countries.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class TestApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    setTheme(R.style.Theme_Countries)

    startKoin {
      androidContext(this@TestApplication)
      modules(applicationModule, testModule)
    }
  }

  override fun onTerminate() {
    super.onTerminate()
    stopKoin()
  }
}
