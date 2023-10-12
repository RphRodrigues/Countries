package br.com.rstudio.countries.presentation.details.screen.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DetailsItemListAdapter(
  private val borderList: List<String>
) : RecyclerView.Adapter<DetailsItemListViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsItemListViewHolder {
    return DetailsItemListViewHolder.create(parent)
  }

  override fun getItemCount(): Int = borderList.size

  override fun onBindViewHolder(holder: DetailsItemListViewHolder, position: Int) {
    holder.bindData(borderList[position])
  }
}