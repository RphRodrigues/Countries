package br.com.rstudio.countries.arch.observability.crashlytics

import android.util.Log
import timber.log.Timber

class FirebaseCrashlyticsReportTree(
  private val crashlyticsReport: CrashlyticsReport
) : Timber.Tree() {

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    if (priority != Log.ERROR && priority != Log.WARN) return

    val tagging = tag ?: "NO_TAG"
    val err = t ?: Exception("$tagging: $message")

    crashlyticsReport.sendException(err)
  }
}
