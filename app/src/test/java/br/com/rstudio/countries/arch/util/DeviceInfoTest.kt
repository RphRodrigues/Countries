package br.com.rstudio.countries.arch.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rstudio.countries.BuildConfig
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeviceInfoTest {

  @Test
  fun `verify all device info properties`() {
    assertEquals("unknown", DeviceInfo.Hardware.manufacturer)
    assertEquals("robolectric", DeviceInfo.Hardware.model)
    assertEquals("Android", DeviceInfo.Hardware.brand)
    assertEquals("unknown", DeviceInfo.Hardware.board)
    assertEquals("robolectric", DeviceInfo.Hardware.hardware_name)
    assertEquals(true, DeviceInfo.Hardware.isEmulator)

    assertEquals("US", DeviceInfo.System.country)
    assertEquals("en-US", DeviceInfo.System.language)
    assertEquals("9", DeviceInfo.System.version)
    assertEquals("28", DeviceInfo.System.sdkVersion)
    assertEquals("sdk_phone_x86-userdebug 9 PKR1.180725.002 4913185 test-keys", DeviceInfo.System.buildNumber)

    assertEquals(BuildConfig.VERSION_NAME, DeviceInfo.AppInfo.appVersion)
    assertEquals(BuildConfig.APPLICATION_ID, DeviceInfo.AppInfo.applicationId)
  }
}
