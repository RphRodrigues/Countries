package br.com.rstudio.countries.presentation.commonview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.rstudio.countries.R

class AppBarView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

  val iconView: AppCompatImageButton by lazy { findViewById(R.id.app_bar_image_button) }
  val titleView: AppCompatTextView by lazy { findViewById(R.id.app_bar_title_text_view) }

  init {
    LayoutInflater.from(context).inflate(R.layout.app_bar_view, this, true)

    attrs?.let {
      val typedArray = context.obtainStyledAttributes(it, R.styleable.AppBarView)

      val title = typedArray.getString(R.styleable.AppBarView_title)
      val titleColor = typedArray.getColor(R.styleable.AppBarView_titleColor, Color.BLACK)
      val showIcon = typedArray.getBoolean(R.styleable.AppBarView_showIcon, false)
      val iconResource = typedArray.getDrawable(R.styleable.AppBarView_icon)

      titleView.text = title
      titleView.setTextColor(titleColor)

      iconView.setImageDrawable(iconResource)
      iconView.visibility = if (showIcon) View.VISIBLE else View.INVISIBLE

      typedArray.recycle()
    }
  }
}
