package br.com.rstudio.countries.arch.extension

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

val Fragment.TAG: String
  get() = this.javaClass.name

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
