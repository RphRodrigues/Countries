@file:Suppress("ChainWrapping")
package br.com.rstudio.countries.arch.util

import android.os.Build
import br.com.rstudio.countries.BuildConfig
import java.util.Locale

class DeviceInfo {

  object Hardware {
    val manufacturer: String
      get() = Build.MANUFACTURER

    val model: String
      get() = Build.MODEL

    val board: String
      get() = Build.BOARD

    val hardware_name: String
      get() = Build.HARDWARE

    val brand: String
      get() = Build.BRAND

    val isEmulator: Boolean
      get() {
        return (
          Build.MANUFACTURER.contains("Genymotion")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.lowercase().contains("droid4x")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("robolectric")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.HARDWARE == "goldfish"
            || Build.HARDWARE == "vbox86"
            || Build.HARDWARE.lowercase().contains("nox")
            || Build.FINGERPRINT.startsWith("generic")
            || Build.PRODUCT == "sdk"
            || Build.PRODUCT == "google_sdk"
            || Build.PRODUCT == "sdk_x86"
            || Build.PRODUCT == "vbox86p"
            || Build.PRODUCT.lowercase().contains("nox")
            || Build.BOARD.lowercase().contains("nox")
            || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
          )
      }
  }

  object System {
    val country: String
      get() = Locale.getDefault().country

    val language: String
      get() = Locale.getDefault().toLanguageTag()

    val version: String
      get() = Build.VERSION.RELEASE

    val sdkVersion: String
      get() = Build.VERSION.SDK_INT.toString()

    val buildNumber: String
      get() = Build.DISPLAY
  }

  object AppInfo {
    val appVersion: String
      get() = BuildConfig.VERSION_NAME

    val applicationId: String
      get() = BuildConfig.APPLICATION_ID
  }
}
