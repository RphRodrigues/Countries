package br.com.rstudio.countries.arch.observability

import io.mockk.Called
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class CrashlyticsReportingTreeTest {

  private lateinit var tree: Timber.Tree
  private lateinit var crashlyticsReportingTree: Timber.Tree
  private lateinit var crashlyticsReporting: CrashlyticsReporting

  @Before
  fun setUp() {
    crashlyticsReporting = mockk(relaxed = true)
    tree = CrashlyticsReportingTree(crashlyticsReporting)
    Timber.plant(tree)
    crashlyticsReportingTree = Timber.asTree()
  }

  @After
  fun tearDown() {
    Timber.uproot(tree)
  }

  @Test
  fun `when debug log then shouldn't reporting it`() {
    Timber.d(MESSAGE)

    verify { crashlyticsReporting wasNot Called }
  }

  @Test
  fun `when info log then shouldn't reporting it`() {
    Timber.i(MESSAGE)

    verify { crashlyticsReporting wasNot Called }
  }

  @Test
  fun `when verbose log then shouldn't reporting it`() {
    Timber.v(MESSAGE)

    verify { crashlyticsReporting wasNot Called }
  }

  @Test
  fun `when warning log then shouldn't reporting it`() {
    Timber.w(MESSAGE)

    verify { crashlyticsReporting wasNot Called }
  }

  @Test
  fun `when wtf log then shouldn't reporting it`() {
    Timber.wtf(MESSAGE)

    verify { crashlyticsReporting wasNot Called }
  }

  @Test
  fun `when error log with message then should reporting it`() {
    Timber.e(MESSAGE)

    verify { crashlyticsReporting.sendException(any()) }
  }

  @Test
  fun `when error log with exception then should reporting it`() {
    val exception = Exception(MESSAGE)

    Timber.e(exception)

    verify { crashlyticsReporting.sendException(exception) }
  }

  @Test
  fun `when error log with tag then should reporting it`() {
    val exception = slot<Throwable>()

    Timber.tag(TAG).e(MESSAGE)

    verify { crashlyticsReporting.sendException(capture(exception)) }
    Assert.assertEquals("$TAG: $MESSAGE", exception.captured.message)
  }

  companion object {
    private const val TAG = "tag"
    private const val MESSAGE = "message"
  }
}
