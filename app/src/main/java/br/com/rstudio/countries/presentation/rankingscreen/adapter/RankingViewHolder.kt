package br.com.rstudio.countries.presentation.rankingscreen.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.inflateView

class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  private val positionView: TextView by lazy { itemView.findViewById(R.id.ranking_item_position_text_view) }
  private val medalView: AppCompatImageView by lazy { itemView.findViewById(R.id.ranking_item_medal_icon_image_view) }

  fun bind(item: String) {
    positionView.text = item

    when (item) {
      FIRST_PLACE -> {
        medalView.setImageResource(R.drawable.gold_medal)
        medalView.isVisible = true
      }
      SECOND_PLACE -> {
        medalView.setImageResource(R.drawable.silver_medal)
        medalView.isVisible = true
      }
      THIRD_PLACE -> {
        medalView.setImageResource(R.drawable.bronze_medal)
        medalView.isVisible = true
      }
      else -> medalView.isVisible = false
    }
  }

  companion object {
    private const val FIRST_PLACE = "1"
    private const val SECOND_PLACE = "2"
    private const val THIRD_PLACE = "3"

    fun create(parent: ViewGroup): RankingViewHolder {
      return RankingViewHolder(parent.inflateView(R.layout.ranking_list_item))
    }
  }
}
