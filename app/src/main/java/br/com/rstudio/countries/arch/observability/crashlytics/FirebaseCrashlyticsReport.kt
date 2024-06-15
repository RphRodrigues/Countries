package br.com.rstudio.countries.arch.observability.crashlytics

import br.com.rstudio.countries.arch.util.DeviceInfo
import com.google.firebase.crashlytics.FirebaseCrashlytics

interface CrashlyticsReport {
  fun sendException(err: Throwable)
}

class FirebaseCrashlyticsReport(
  private val firebaseCrashlytics: FirebaseCrashlytics
) : CrashlyticsReport {

  init {
    firebaseCrashlytics.setCustomKey(COUNTRY, DeviceInfo.System.country)
    firebaseCrashlytics.setCustomKey(LANGUAGE, DeviceInfo.System.language)
    firebaseCrashlytics.setCustomKey(IS_EMULATOR, DeviceInfo.Hardware.isEmulator)
  }

  override fun sendException(err: Throwable) {
    firebaseCrashlytics.recordException(err)
  }

  companion object {
    const val COUNTRY = "country"
    const val LANGUAGE = "language"
    const val IS_EMULATOR = "is_emulator"
  }
}
