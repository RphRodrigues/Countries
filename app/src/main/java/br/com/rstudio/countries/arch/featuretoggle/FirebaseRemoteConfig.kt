package br.com.rstudio.countries.arch.featuretoggle

import br.com.rstudio.countries.BuildConfig
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.util.Constants
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import timber.log.Timber

interface RemoteConfig {
  fun fetchConfigs()
  fun getBoolean(key: String): Boolean
  fun getString(key: String): String
  fun getLong(key: String): Long
  fun getDouble(key: String): Double
}

class FirebaseRemoteConfigImp(private val remoteConfig: FirebaseRemoteConfig) : RemoteConfig {

  init {
    val configSettings = FirebaseRemoteConfigSettings.Builder()
      .setMinimumFetchIntervalInSeconds(BuildConfig.REMOTE_CONFIG_INTERVAL)
      .build()

    remoteConfig.setConfigSettingsAsync(configSettings)
    remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
  }

  override fun fetchConfigs() {
    remoteConfig.fetchAndActivate()
      .addOnCompleteListener { task ->
        if (task.isSuccessful) {
          Timber.tag(Constants.REMOTE_CONFIG).d("Remote config updated: ${task.result}")
        } else {
          Timber.tag(Constants.REMOTE_CONFIG).w("Remote config not updated")
        }
      }
  }

  override fun getBoolean(key: String) =
    remoteConfig.getBoolean(key).also { value ->
      Timber.tag(Constants.REMOTE_CONFIG).d("key: $key | value: $value")
    }

  override fun getString(key: String) =
    remoteConfig.getString(key).also { value ->
      Timber.tag(Constants.REMOTE_CONFIG).d("key: $key | value: $value")
    }

  override fun getLong(key: String) =
    remoteConfig.getLong(key).also { value ->
      Timber.tag(Constants.REMOTE_CONFIG).d("key: $key | value: $value")
    }

  override fun getDouble(key: String) =
    remoteConfig.getDouble(key).also { value ->
      Timber.tag(Constants.REMOTE_CONFIG).d("key: $key | value: $value")
    }
}
