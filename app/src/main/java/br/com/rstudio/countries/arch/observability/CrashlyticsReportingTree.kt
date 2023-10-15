package br.com.rstudio.countries.arch.observability

import android.util.Log
import timber.log.Timber

class CrashlyticsReportingTree(
  private val crashlyticsReporting: CrashlyticsReporting
) : Timber.Tree() {

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    if (priority != Log.ERROR) return

    val tagging = tag ?: "NO_TAG"
    val err = t ?: Exception("$tagging: $message")

    crashlyticsReporting.sendException(err)
  }
}
