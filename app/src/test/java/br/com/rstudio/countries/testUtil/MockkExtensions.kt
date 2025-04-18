package br.com.rstudio.countries.testUtil

import io.mockk.MockKVerificationScope
import io.mockk.coVerify
import io.mockk.verify

fun verifyOnce(verifyBlock: MockKVerificationScope.() -> Unit) {
  verify(exactly = 1) {
    verifyBlock()
  }
}

fun coVerifyOnce(verifyBlock: suspend MockKVerificationScope.() -> Unit) {
  coVerify(exactly = 1) {
    verifyBlock()
  }
}
