package br.com.rstudio.countries.arch.extension

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

private const val DELAY = 500L

fun ViewGroup.inflateView(@LayoutRes layout: Int): View {
  return LayoutInflater.from(context).inflate(layout, this, false)
}

fun View.setSafeOnClickListener(onClickCallback: () -> Unit) {
  setOnClickListener {
    isEnabled = false

    Handler(Looper.getMainLooper())
      .postDelayed(
        {
          isEnabled = true
        },
        DELAY
      )

    onClickCallback.invoke()
  }
}
