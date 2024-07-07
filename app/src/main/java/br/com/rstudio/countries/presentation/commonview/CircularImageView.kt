package br.com.rstudio.countries.presentation.commonview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.rstudio.countries.R

class CircularImageView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  init {
    LayoutInflater.from(context).inflate(R.layout.circular_image_view, this, true)
  }
}
