package br.com.rstudio.countries.arch.extension

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.base.BaseActivityView
import br.com.rstudio.countries.arch.model.ErrorModel
import com.google.android.material.bottomnavigation.BottomNavigationView

val Activity.ScreenName: String
  get() = this.javaClass.simpleName

fun Activity?.showError(err: ErrorModel, errAction: () -> Unit) {
  (this as? BaseActivityView)?.showError(err, errAction)
}

fun Activity?.hideError() {
  (this as? BaseActivityView)?.hideError()
}

fun Activity?.showLoader() {
  (this as? BaseActivityView)?.showLoader()
}

fun Activity?.hideLoader() {
  (this as? BaseActivityView)?.hideLoader()
}

fun FragmentActivity?.replaceFragment(fragment: Fragment) = this?.run {
  supportFragmentManager
    .beginTransaction()
    .replace(R.id.frame_layout, fragment)
    .addToBackStack(fragment.ScreenName)
    .commit()
}

fun FragmentActivity?.popBackStack() = this?.run {
  supportFragmentManager.popBackStack()
}

fun Activity?.goToBottomNavigationHome() {
  if (this == null) return

  findViewById<BottomNavigationView>(R.id.bottom_navigation)?.selectedItemId = R.id.action_home
}
