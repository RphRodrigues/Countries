package br.com.rstudio.countries.presentation.overviewscreen.v1.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import br.com.rstudio.countries.R

class DetailsItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val iconView: ImageView by lazy { findViewById(R.id.details_icon_image_view) }
  private val textView: TextView by lazy { findViewById(R.id.details_text_view) }

  init {
    LayoutInflater.from(context).inflate(R.layout.details_item, this, true)
  }

  fun bindItem(@DrawableRes icon: Int?, text: String) {
    bindIcon(icon)
    bindText(text)
  }

  private fun bindIcon(@DrawableRes icon: Int?) = icon?.let {
    iconView.setImageDrawable(AppCompatResources.getDrawable(context, icon))
    iconView.isVisible = true
  } ?: run {
    iconView.isVisible = false
  }

  private fun bindText(text: String) {
    textView.text = text
  }

  fun bindTag(tag: String) {
    iconView.tag = tag
  }
}
