package br.com.rstudio.countries.arch.observability.analytics

import android.os.Bundle
import androidx.core.os.bundleOf
import br.com.rstudio.countries.arch.util.DeviceInfo
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber

interface AnalyticsReport {
  fun trackScreenView(screenName: String)
  fun trackEvent(action: String, params: Map<String, String?>? = null)
}

class FirebaseAnalyticsReport(private val analytics: FirebaseAnalytics) : AnalyticsReport {

  init {
    with(analytics) {
      setUserProperty(UserProperty.MODEL, DeviceInfo.Hardware.model)
      setUserProperty(UserProperty.BRAND, DeviceInfo.Hardware.brand)
      setUserProperty(UserProperty.MANUFACTURER, DeviceInfo.Hardware.manufacturer)
      setUserProperty(UserProperty.IS_EMULATOR, DeviceInfo.Hardware.isEmulator.toString())

      setUserProperty(UserProperty.COUNTRY, DeviceInfo.System.country)
      setUserProperty(UserProperty.LANGUAGE, DeviceInfo.System.language)
      setUserProperty(UserProperty.SYSTEM_VERSION, DeviceInfo.System.version)
      setUserProperty(UserProperty.SDK_VERSION, DeviceInfo.System.sdkVersion)

      setUserProperty(UserProperty.APP_VERSION, DeviceInfo.AppInfo.appVersion)
    }
  }

  override fun trackScreenView(screenName: String) {
    bundleOf(AnalyticsParam.SCREEN_NAME to screenName)
      .also { params ->
        val action = AnalyticsParam.SCREEN_VIEW

        printLog(action, params)
        analytics.logEvent(action, params)
      }
  }

  override fun trackEvent(action: String, params: Map<String, String?>?) {
    val bundle = Bundle()

    params?.keys?.forEach { key ->
      params[key]?.let { value ->
        if (value.isNotEmpty()) {
          bundle.putString(key, value)
        }
      }
    }

    printLog(action, bundle)
    analytics.logEvent(action, bundle)
  }

  private fun printLog(action: String, params: Bundle?) {
    Timber.tag("APP_ANALYTICS").d("############ APP ANALYTICS ############")
    Timber.tag("APP_ANALYTICS").d(" ")
    Timber.tag("APP_ANALYTICS").d("action -> $action")

    params?.keySet()?.forEach { key ->
      params.getString(key)?.let { value ->
        Timber.tag("APP_ANALYTICS").d("param -> $key: $value")
      }
    }

    Timber.tag("APP_ANALYTICS").d(" ")
    Timber.tag("APP_ANALYTICS").d("#######################################")
  }
}
