package br.com.rstudio.countries.presentation.profilescreen.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.LinearLayoutCompat
import br.com.rstudio.countries.R

class ProfileRankingView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

  private val iconView: ImageView by lazy { findViewById(R.id.profile_ranking_icon) }
  private val titleView: TextView by lazy { findViewById(R.id.profile_ranking_title) }
  private val valueView: TextView by lazy { findViewById(R.id.profile_ranking_value) }

  init {
    LayoutInflater.from(context).inflate(R.layout.profile_ranking_view, this, true)
  }

  fun bindIcon(@DrawableRes icon: Int) {
    iconView.setImageResource(icon)
  }

  fun bindTitle(title: String) {
    titleView.text = title
  }

  fun bindValue(value: String) {
    valueView.text = value
  }
}
