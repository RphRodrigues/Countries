package br.com.rstudio.countries.presentation.overviewscreen.v2.view

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.bold
import br.com.rstudio.countries.R

class CountryOverviewItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val textView: TextView by lazy { findViewById(R.id.overview_text_view) }

  init {
    LayoutInflater.from(context).inflate(R.layout.overview_item, this, true)
  }

  fun bindItem(item: String, text: String) {
    val builder = SpannableStringBuilder().apply {
      bold { append(item) }
      append(": $text")
    }

    textView.text = builder
  }
}
