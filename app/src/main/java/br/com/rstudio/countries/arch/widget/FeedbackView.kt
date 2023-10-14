package br.com.rstudio.countries.arch.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.rstudio.countries.R
import com.google.android.material.button.MaterialButton

class FeedbackView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val title: TextView by lazy { findViewById(R.id.feedback_title) }
  private val message: TextView by lazy { findViewById(R.id.feedback_message) }
  private val button: MaterialButton by lazy { findViewById(R.id.feedback_button) }

  init {
    LayoutInflater.from(context).inflate(R.layout.feedback_view, this, true)
  }

  fun setTitle(text: String) {
    title.text = text
  }

  fun setMessage(text: String) {
    message.text = text
  }

  fun setMessage(@StringRes text: Int) {
    message.text = context?.getText(text)
  }

  fun setupButton(text: String, onClick: () -> Unit) {
    button.text = text
    setOnClickListener { onClick() }
  }

  fun setupButton(@StringRes text: Int, onClick: () -> Unit) {
    button.text = context?.getText(text)
    setOnClickListener { onClick() }
  }

  fun setOnClickListener(onClick: () -> Unit) {
    button.setOnClickListener { onClick() }
  }
}
