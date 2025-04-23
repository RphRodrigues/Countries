package br.com.rstudio.countries.presentation.homescreen.v2.adapter.header

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.inflateView
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.homescreen.v2.adapter.ListViewHolder

class HeaderViewHolder(itemView: View) : ListViewHolder(itemView) {

  private val recyclerView: RecyclerView by lazy {
    itemView.findViewById(R.id.most_popular_recycler_view)
  }

  init {
    recyclerView.adapter = MostPopularAdapter()
  }

  fun bindData(items: List<Country>, onClickListener: (Country) -> Unit) {
    (recyclerView.adapter as MostPopularAdapter).addItems(items, onClickListener)
  }

  companion object {
    fun create(parent: ViewGroup): HeaderViewHolder {
      val view = parent.inflateView(R.layout.header_view_type_v2)
      return HeaderViewHolder(view)
    }
  }
}
