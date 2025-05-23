package br.com.rstudio.countries.presentation.homescreen.v2.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.inflateView
import br.com.rstudio.countries.presentation.homescreen.v1.model.ContinentVO
import br.com.rstudio.countries.presentation.homescreen.v2.adapter.country.CountryAdapter
import org.koin.core.component.KoinComponent

class MainViewHolder(itemView: View) : ListViewHolder(itemView), KoinComponent {

  private val title: TextView by lazy { itemView.findViewById(R.id.title_item) }
  private val recyclerView: RecyclerView by lazy { itemView.findViewById(R.id.recycler_view_item) }

  init {
    recyclerView.adapter = CountryAdapter()
    recyclerView.setHasFixedSize(true)
  }

  fun bindData(item: ContinentVO) {
    title.text = item.name
    (recyclerView.adapter as CountryAdapter).addItems(item.countries, item.onClickListener)
  }

  companion object {
    fun create(parent: ViewGroup): MainViewHolder {
      val view = parent.inflateView(R.layout.main_list_item)
      return MainViewHolder(view)
    }
  }
}
