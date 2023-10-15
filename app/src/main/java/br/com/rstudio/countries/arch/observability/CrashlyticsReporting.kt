package br.com.rstudio.countries.arch.observability

import com.google.firebase.crashlytics.FirebaseCrashlytics

interface CrashlyticsReporting {
  fun sendException(err: Throwable)
}

class FirebaseCrashlyticsReporting(
  private val firebaseCrashlytics: FirebaseCrashlytics
) : CrashlyticsReporting {
  override fun sendException(err: Throwable) {
    firebaseCrashlytics.recordException(err)
  }
}
