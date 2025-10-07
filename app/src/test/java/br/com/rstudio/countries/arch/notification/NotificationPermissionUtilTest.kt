package br.com.rstudio.countries.arch.notification

import br.com.rstudio.countries.arch.prefs.AppPrefs
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NotificationPermissionUtilTest {

  private lateinit var appPrefs: AppPrefs
  private lateinit var notificationUtil: NotificationPermissionUtil

  @Before
  fun setup() {
    appPrefs = mockk()
    notificationUtil = NotificationPermissionUtil(context = mockk(relaxed = true), appPrefs)
  }

  @Test
  fun `should return true when user navigated and time passed more than one day`() {
    every { appPrefs.didUserNavigateThroughApp() } returns true
    val pastTime = System.currentTimeMillis() - (86400000L + 1000)
    every { appPrefs.getLastAskedNotificationPermissionAt() } returns pastTime

    val result = notificationUtil.shouldAskNotificationPermissionAgain()

    assertTrue(result)
  }

  @Test
  fun `should return false when user did not navigate`() {
    every { appPrefs.didUserNavigateThroughApp() } returns false
    every { appPrefs.getLastAskedNotificationPermissionAt() } returns 0L

    val result = notificationUtil.shouldAskNotificationPermissionAgain()

    assertFalse(result)
  }

  @Test
  fun `should return false when elapsed time is less than one day`() {
    every { appPrefs.didUserNavigateThroughApp() } returns true
    val recentTime = System.currentTimeMillis() - (86400000L - 1000)
    every { appPrefs.getLastAskedNotificationPermissionAt() } returns recentTime

    val result = notificationUtil.shouldAskNotificationPermissionAgain()

    assertFalse(result)
  }
}
