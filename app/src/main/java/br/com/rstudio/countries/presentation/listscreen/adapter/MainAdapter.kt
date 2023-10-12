package br.com.rstudio.countries.presentation.listscreen.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.presentation.listscreen.model.ContinentVO
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

private const val VIEW_TYPE_1 = 1
private const val VIEW_TYPE_2 = 2

class MainAdapter : RecyclerView.Adapter<ListViewHolder>() {

  private var continentList: List<ContinentVO> = listOf()

  @SuppressLint("NotifyDataSetChanged")
  fun addItems(continentList: List<ContinentVO>) {
    this.continentList = continentList
    notifyDataSetChanged()
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
    if (holder is MainViewHolder) {
      holder.bindData(continentList[position -1])
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (position == 0) VIEW_TYPE_1 else VIEW_TYPE_2
  }
}
