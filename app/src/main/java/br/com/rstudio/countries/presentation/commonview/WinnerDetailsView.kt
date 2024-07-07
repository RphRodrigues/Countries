package br.com.rstudio.countries.presentation.commonview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.rstudio.countries.R

class WinnerDetailsView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val winnerMedal: AppCompatImageView by lazy { findViewById(R.id.winner_medal_image_view) }
  private val tag: LinearLayoutCompat by lazy { findViewById(R.id.winner_point_tag) }

  init {
    LayoutInflater.from(context).inflate(R.layout.winner_details_view, this, true)

    attrs?.let {
      val typedArray = context.obtainStyledAttributes(it, R.styleable.WinnerDetailsView)
      val textColor = typedArray.getColor(R.styleable.WinnerDetailsView_tagColor, Color.BLACK)
      val showWinnerMedal = typedArray.getBoolean(R.styleable.WinnerDetailsView_showWinnerMedal, false)

      tag.backgroundTintList = ColorStateList.valueOf(textColor)
      winnerMedal.visibility = if (showWinnerMedal) View.VISIBLE else View.INVISIBLE

      typedArray.recycle()
    }
  }
}
