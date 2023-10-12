package br.com.rstudio.countries.arch.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.rstudio.countries.R
import com.google.android.material.button.MaterialButton
import org.koin.core.KoinApplication.Companion.init

class ProgressView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val title: TextView by lazy {
    findViewById(R.id.progress_message)
  }

  init {
    LayoutInflater.from(context).inflate(R.layout.progress_view, this, true)
  }

  fun setTitle(text: String) {
    title.text = text
  }
}