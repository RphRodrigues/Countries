package br.com.rstudio.countries.arch.notification

import br.com.rstudio.countries.arch.prefs.AppPrefs
import br.com.rstudio.countries.arch.util.Constants
import timber.log.Timber

/**
 * Utility class to determine whether the app should request the notification permission again.
 *
 * This decision is based on whether the user has previously navigated through the app
 * and whether a certain amount of time has passed since the last permission request.
 *
 * @param appPrefs Abstraction over shared preferences for storing and retrieving app-specific settings.
 */
class NotificationPermissionUtil(private val appPrefs: AppPrefs) {

  /**
   * Determines whether the app should prompt the user for notification permission again.
   *
   * The permission will be requested again only if:
   * - The user has navigated through the app (indicating engagement).
   * - At least 24 hours have passed since the last request.
   *
   * @return `true` if the permission should be requested again, `false` otherwise.
   */
  fun shouldAskNotificationPermissionAgain(): Boolean {
    val lastAskedAt = appPrefs.getLastAskedNotificationPermissionAt()
    val elapsed = System.currentTimeMillis() - lastAskedAt

    val shouldAsk = appPrefs.didUserNavigateThroughApp() && elapsed > ONE_DAY

    Timber.tag(Constants.APP_PREFS).d("Should ask notification permission again? $shouldAsk")
    return shouldAsk
  }

  companion object {
    /** Constant representing one day in milliseconds (24 * 60 * 60 * 1000). */
    private const val ONE_DAY = 86400000L
  }
}
