package br.com.rstudio.countries.arch.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.rstudio.countries.arch.util.Constants
import timber.log.Timber

class AppPrefs(private val sharedPrefs: SharedPreferences) {

  fun setUserNavigatedThroughApp() {
    sharedPrefs.edit {
      putBoolean("user_navigated_through_app", true)
      Timber.tag(Constants.APP_PREFS).d("set user navigated through app as true")
    }
  }

  fun didUserNavigateThroughApp(): Boolean {
    return sharedPrefs.getBoolean("user_navigated_through_app", false).also {
      Timber.tag(Constants.APP_PREFS).d("did user navigated through app? $it")
    }
  }

  fun cleanLastAskedTimeNotificationPermission() {
    sharedPrefs.edit {
      putLong("asked_notification_permission_at", 0L)
      Timber.tag(Constants.APP_PREFS).d("clean last asked time notification permission")
    }
  }

  fun setLastAskedTimeNotificationPermission() {
    sharedPrefs.edit {
      putLong("asked_notification_permission_at", System.currentTimeMillis())
      Timber.tag(Constants.APP_PREFS).d("set last asked time notification permission")
    }
  }

  fun getLastAskedNotificationPermissionAt(): Long {
    return sharedPrefs.getLong("asked_notification_permission_at", 0L).also {
      Timber.tag(Constants.APP_PREFS).d("get last asked notification permission at")
    }
  }

  companion object {
    const val PREF_NAME = "countries_app_prefs"
  }
}
