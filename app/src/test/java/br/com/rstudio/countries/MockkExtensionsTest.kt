package br.com.rstudio.countries

import br.com.rstudio.countries.testUtil.verifyOnce
import io.mockk.mockk
import org.junit.Test

class MockkExtensionsTest {

  private val fakeDependency: Dependency = mockk(relaxed = true)
  private val fakeClass = Fake(fakeDependency)

  @Test
  fun `when verify once is called with one calls then it should pass`() {
    fakeClass.doSomething()

    verifyOnce {
      fakeDependency.doSomething()
    }
  }

  @Test(expected = java.lang.AssertionError::class)
  fun `when verify once is called with more than one calls then it should throw Assertion Error`() {
    fakeClass.doSomething()
    fakeClass.doSomething()

    verifyOnce {
      fakeDependency.doSomething()
    }
  }

  class Dependency { fun doSomething() = Unit }
  class Fake(private val fakeDependency: Dependency) {
    fun doSomething() = fakeDependency.doSomething()
  }
}
