package br.com.rstudio.countries.arch.observability.datadog

import android.content.Context
import android.util.Log
import br.com.rstudio.countries.BuildConfig
import br.com.rstudio.countries.BuildConfig.DATADOG_APPLICATION_ID
import br.com.rstudio.countries.BuildConfig.DATADOG_CLIENT_TOKEN
import br.com.rstudio.countries.arch.util.Constants
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.log.Logs
import com.datadog.android.log.LogsConfiguration
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.Rum
import com.datadog.android.rum.RumConfiguration
import com.datadog.android.rum.tracking.MixedViewTrackingStrategy
import com.datadog.android.trace.AndroidTracer
import com.datadog.android.trace.Trace
import com.datadog.android.trace.TraceConfiguration
import io.opentracing.util.GlobalTracer
import timber.log.Timber

@Suppress("KotlinConstantConditions")
class DatadogInitializer {

  fun initialize(context: Context) {
    if (Datadog.isInitialized()) return
    if (DATADOG_CLIENT_TOKEN == "null" || DATADOG_APPLICATION_ID == "null") return

    val configuration =
      Configuration.Builder(
        clientToken = DATADOG_CLIENT_TOKEN,
        env = BuildConfig.BUILD_TYPE,
        variant = ""
      )
        .useSite(DatadogSite.US5)
        .setCrashReportsEnabled(true)
        .build()

    Datadog.initialize(context, configuration, TrackingConsent.GRANTED)

    if (BuildConfig.DEBUG) {
      Datadog.setVerbosity(Log.WARN)
    }

    Rum.enable(
      RumConfiguration.Builder(DATADOG_APPLICATION_ID)
        .trackUserInteractions()
        .trackNonFatalAnrs(true)
        .trackBackgroundEvents(true)
        // Sample rate is a percentage number of sessions that will be send to datadog.
        .setSessionSampleRate(SESSION_RATE)
        .trackLongTasks(longTaskThresholdMs = 100L)
        .setViewEventMapper { event ->
          Timber.tag(Constants.DATADOG).d("event $event")
          event
        }
        .useViewTrackingStrategy(MixedViewTrackingStrategy(trackExtras = true))
        .build()
    )

    Trace.enable(
      TraceConfiguration.Builder()
        .setNetworkInfoEnabled(true)
        .build()
    )

    GlobalTracer.registerIfAbsent(
      AndroidTracer.Builder()
        .setBundleWithRumEnabled(true)
        .setSampleRate(SESSION_RATE.toDouble())
        .setPartialFlushThreshold(PARTIAL_FLUSH_THRESHOLD)
        .build()
    )

    Logs.enable(LogsConfiguration.Builder().build())

    if (Datadog.isInitialized()) {
      Timber.tag(Constants.DATADOG).d("datadog was initialized")
    }
  }

  companion object {
    private const val SESSION_RATE = 100.0f
    private const val PARTIAL_FLUSH_THRESHOLD = 10
  }
}
