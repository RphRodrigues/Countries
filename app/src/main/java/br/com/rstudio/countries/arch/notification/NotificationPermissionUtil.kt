package br.com.rstudio.countries.arch.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
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
class NotificationPermissionUtil(
  context: Context,
  private val appPrefs: AppPrefs
) {

  private val appContext = context.applicationContext

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

  /**
   * Determines whether the app should request the notification permission.
   *
   * This method checks if the `POST_NOTIFICATIONS` permission has not been granted
   * and whether the app's internal logic (e.g., based on user behavior or elapsed time)
   * deems it's appropriate to ask again.
   *
   * @return `true` if the permission is not granted and the app should ask again, `false` otherwise.
   */
  @SuppressLint("InlinedApi")
  fun shouldAskedNotificationPermission() =
    ContextCompat.checkSelfPermission(
      appContext,
      Manifest.permission.POST_NOTIFICATIONS
    ) != PackageManager.PERMISSION_GRANTED && shouldAskNotificationPermissionAgain()

  /**
   * Triggers the notification permission request flow for Android 13+ (TIRAMISU).
   *
   * This method checks if the app is running on a compatible Android version and whether
   * the notification permission should be requested. If the user has previously denied
   * the permission, a rationale should be shown before redirecting to system settings.
   *
   * Based on the situation, this function calls a provided callback to show a custom UI
   * (e.g., a bottom sheet) with an explanation.
   *
   * - If the user denied the permission once, `redirectToSettings` will be `true`.
   * - If it's the first time asking (or the user selected "Don't ask again"),
   *   `redirectToSettings` will be `false`.
   *
   * The permission request timestamp is saved to avoid re-prompting too frequently.
   *
   * @param activity The current Activity context used to check permission rationale.
   * @param showNotificationJustification A callback that should show a justification UI
   * before requesting permission. The Boolean parameter indicates whether the user should
   * be redirected to system settings.
   */
  fun askNotificationPermission(
    activity: Activity,
    showNotificationJustification: (redirectToSettings: Boolean) -> Unit
  ) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    if (!shouldAskedNotificationPermission()) return

    when {
      shouldShowRequestPermissionRationale(activity, Manifest.permission.POST_NOTIFICATIONS) -> {
        Timber.tag(Constants.PERMISSION).d("User de nied once — show a custom dialog explaining why you need it")
        showNotificationJustification(true)
      }

      ContextCompat.checkSelfPermission(
        appContext,
        Manifest.permission.POST_NOTIFICATIONS
      ) != PackageManager.PERMISSION_GRANTED -> {
        Timber.tag(Constants.PERMISSION).d("First time asking, or user chose \"Don't ask again\"")
        showNotificationJustification(false)
      }
    }

    appPrefs.setLastAskedTimeNotificationPermission()
  }

  companion object {
    /** Constant representing one day in milliseconds (24 * 60 * 60 * 1000). */
    private const val ONE_DAY = 86400000L
  }
}
