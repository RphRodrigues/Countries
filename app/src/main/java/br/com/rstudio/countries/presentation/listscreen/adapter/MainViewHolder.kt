package br.com.rstudio.countries.presentation.listscreen.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.inflateView
import br.com.rstudio.countries.presentation.listscreen.adapter.country.adapter.CountryAdapter
import br.com.rstudio.countries.presentation.listscreen.model.ContinentVO
import org.koin.core.component.KoinComponent

class MainViewHolder(itemView: View) : ListViewHolder(itemView), KoinComponent {

//  private val recyclerViewPositionHolder by inject<RecyclerViewPositionHolder>()
  private val title: TextView by lazy { itemView.findViewById(R.id.title_item) }
  private val recyclerView: RecyclerView by lazy { itemView.findViewById(R.id.recycler_view_item) }

  init {
    recyclerView.adapter = CountryAdapter()
    recyclerView.setHasFixedSize(true)

//    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//      override fun onScrolled(recyclerView: RecyclerView, x: Int, y: Int) {
//        super.onScrolled(recyclerView, x, y)
//        println(":: recyclerViewPositionHolder.positions - ${recyclerViewPositionHolder.positions} :: ")
//        recyclerViewPositionHolder.positions[title.text.toString()] = listOf(x, y)
//      }
//    })
//
//    recyclerViewPositionHolder.positions[title.text.toString()].run {
//      val x = this?.get(0) ?: 0
//      val y = this?.get(0) ?: 0
//      println(":: title: ${title.text} = x, y = $x, $y ::")
//      recyclerView.scrollTo(x, y)
//    }

//    recyclerView.listener
  }

  fun bindData(item: ContinentVO) {
    title.text = item.name
    (recyclerView.adapter as CountryAdapter).addItems(item.countries, item.onClickListener)
  }

//  fun bindData(item: ContinentVO, onRecyclerViewCreated: () -> Unit) {
//    title.text = item.name
//    onRecyclerViewCreated.invoke()
//    (recyclerView.adapter as CountryAdapter).addItems(
//      item.countries,
//      item.onClickListener,
//      onRecyclerViewCreated
//    )
//  }

  companion object {
    fun create(parent: ViewGroup): MainViewHolder {
      val view = parent.inflateView(R.layout.main_list_item)
      return MainViewHolder(view)
    }
  }
}
