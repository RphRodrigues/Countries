package br.com.rstudio.countries.arch.extension

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

val Fragment.ScreenName: String
  get() = this.javaClass.simpleName

fun Fragment.setupBackPressedCallback(callback: () -> Unit) {

  val backPressedCallback = object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
      callback.invoke()
    }
  }

  requireActivity()
    .onBackPressedDispatcher
    .addCallback(viewLifecycleOwner, backPressedCallback)
}
