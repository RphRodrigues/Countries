package br.com.rstudio.countries.presentation.details.screen.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.inflateView

class DetailsItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  private val borderView: TextView by lazy { itemView.findViewById(R.id.details_text_view) }

  fun bindData(border: String) {
    borderView.text = border
  }

  companion object {
    fun create(parent: ViewGroup): DetailsItemListViewHolder {
      val view = parent.inflateView(R.layout.details_item)
      return DetailsItemListViewHolder(view)
    }
  }
}
