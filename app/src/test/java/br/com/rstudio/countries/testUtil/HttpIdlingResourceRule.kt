package br.com.rstudio.countries.testUtil

import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class HttpIdlingResourceRule : TestRule {
  override fun apply(base: Statement?, description: Description?): Statement {

    val statement = object : Statement() {
      override fun evaluate() {

        IdlingRegistry.getInstance().register(RetrofitIdlingResource.getIdlingResource())
        try {
          base?.evaluate()
        } finally {
          IdlingRegistry.getInstance().unregister(RetrofitIdlingResource.getIdlingResource())
        }
      }
    }

    return statement
  }
}
