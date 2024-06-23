package br.com.rstudio.countries.testUtil

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers
import org.hamcrest.Matchers.equalTo

object UiVerify {

  fun checkViewIsDisplayed(id: Int) {
    onView(withId(id)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
  }

  fun checkViewIsDisplayed(text: String) {
    onView(withText(text))
      .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
  }

  fun checkViewByTagIsDisplayed(tag: String) {
    onView(withTagValue(equalTo(tag))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
  }

  fun checkViewStartsWithText(id: Int, text: String) {
    onView(withId(id))
      .check(matches(withText(Matchers.startsWith(text))))
      .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
  }

  fun checkText(id: Int, text: Int) {
    onView(withId(id)).check(matches(withText(text)))
  }

  fun click(id: Int) {
    onView(withId(id)).perform(click())
  }

  fun pressBackButton() {
    pressBack()
  }

  fun scrollToPosition(idRecyclerView: Int, itemPosition: Int) {
    onView(withId(idRecyclerView)).perform(scrollToPosition<RecyclerView.ViewHolder>(itemPosition))
  }
}
