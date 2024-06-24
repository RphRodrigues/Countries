package br.com.rstudio.countries.testUtil

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object RetrofitIdlingResource {

  private const val RESOURCE_NAME = "Retrofit"
  private val countingIdlingResource = CountingIdlingResource(RESOURCE_NAME)

  fun increment() {
    countingIdlingResource.increment()
  }

  fun decrement() {
    if (!countingIdlingResource.isIdleNow) {
      countingIdlingResource.decrement()
    }
  }

  fun getIdlingResource(): IdlingResource {
    return countingIdlingResource
  }
}
