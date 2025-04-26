package br.com.rstudio.countries.presentation.rankingscreen.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RankingAdapter(private val items: List<String>) : RecyclerView.Adapter<RankingViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
    return RankingViewHolder.create(parent)
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
    holder.bind(items[position])
  }
}
