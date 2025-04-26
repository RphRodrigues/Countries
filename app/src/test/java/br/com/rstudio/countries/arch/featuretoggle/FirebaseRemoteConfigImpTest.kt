package br.com.rstudio.countries.arch.featuretoggle

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class FirebaseRemoteConfigImpTest {

  private lateinit var remoteConfig: RemoteConfig
  private val mockFirebaseRemoteConfig = mockk<FirebaseRemoteConfig>(relaxed = true)

  @Before
  fun setUp() {
    remoteConfig = FirebaseRemoteConfigImp(mockFirebaseRemoteConfig)
  }

  @Test
  fun `when firebase remote config is initialized then it should configures it`() {

    verify {
      mockFirebaseRemoteConfig.setConfigSettingsAsync(any())
      mockFirebaseRemoteConfig.setDefaultsAsync(any<Int>())
    }
  }

  @Test
  fun `when fetch configs is called then it should call fetchAndActivate`() {
    every { mockFirebaseRemoteConfig.fetchAndActivate() } returns mockk {
      every { addOnCompleteListener(any()) } returns mockk()
    }

    remoteConfig.fetchConfigs()

    verify { mockFirebaseRemoteConfig.fetchAndActivate() }
  }

  @Test
  fun `when get boolean is called then it should call firebase remote config`() {
    remoteConfig.getBoolean("key")

    verify { mockFirebaseRemoteConfig.getBoolean("key") }
  }

  @Test
  fun `when get string is called then it should call firebase remote config`() {
    remoteConfig.getString("key")

    verify { mockFirebaseRemoteConfig.getString("key") }
  }

  @Test
  fun `when get long is called then it should call firebase remote config`() {
    remoteConfig.getLong("key")

    verify { mockFirebaseRemoteConfig.getLong("key") }
  }

  @Test
  fun `when get double is called then it should call firebase remote config`() {
    remoteConfig.getDouble("key")

    verify { mockFirebaseRemoteConfig.getDouble("key") }
  }
}
