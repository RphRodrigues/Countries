package br.com.rstudio.countries.presentation.overviewscreen.v2.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import br.com.rstudio.countries.R

class CountryOverviewHeaderView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val titleView: TextView by lazy { findViewById(R.id.overview_headers_title) }
  private val subtitleView: TextView by lazy { findViewById(R.id.overview_headers_subtitle_view) }

  init {
    LayoutInflater.from(context).inflate(R.layout.overview_item_headers_view, this, true)
  }

  fun bindTitle(text: String) {
    titleView.text = text
  }

  fun bindSubtitle(text: String) {
    subtitleView.text = text
    subtitleView.isVisible = true
  }
}
