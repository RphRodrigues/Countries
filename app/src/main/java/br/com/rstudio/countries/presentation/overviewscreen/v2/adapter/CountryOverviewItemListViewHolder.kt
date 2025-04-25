package br.com.rstudio.countries.presentation.overviewscreen.v2.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.inflateView

class CountryOverviewItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  private val borderView: TextView by lazy { itemView.findViewById(R.id.overview_text_view) }

  fun bindData(border: String) {
    borderView.text = border
  }

  companion object {
    fun create(parent: ViewGroup): CountryOverviewItemListViewHolder {
      val view = parent.inflateView(R.layout.overview_item)
      return CountryOverviewItemListViewHolder(view)
    }
  }
}
