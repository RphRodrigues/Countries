package br.com.rstudio.countries.presentation.homescreen.v2.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.homescreen.v1.model.ContinentVO
import br.com.rstudio.countries.presentation.homescreen.v2.adapter.header.HeaderViewHolder

private const val VIEW_TYPE_1 = 1
private const val VIEW_TYPE_2 = 2

class MainAdapter : RecyclerView.Adapter<ListViewHolder>() {

  private var continentList: List<ContinentVO> = listOf()
  private var mostPopularCountries: List<Country> = listOf()
  private var onClickListener: (Country) -> Unit = {}

  @SuppressLint("NotifyDataSetChanged")
  fun addItems(
    mostPopularCountries: List<Country>,
    continentList: List<ContinentVO>,
    onClickListener: (Country) -> Unit
  ) {
    if (this.continentList.isEmpty()) {
      this.continentList = continentList
      this.onClickListener = onClickListener
      this.mostPopularCountries = mostPopularCountries
      notifyDataSetChanged()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
    return if (viewType == VIEW_TYPE_1) {
      HeaderViewHolder.create(parent)
    } else {
      MainViewHolder.create(parent)
    }
  }

  override fun getItemCount(): Int = continentList.size + 1

  override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    when (holder) {
      is MainViewHolder -> holder.bindData(continentList[position - 1])
      is HeaderViewHolder -> holder.bindData(mostPopularCountries, onClickListener)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (position == 0) VIEW_TYPE_1 else VIEW_TYPE_2
  }
}
