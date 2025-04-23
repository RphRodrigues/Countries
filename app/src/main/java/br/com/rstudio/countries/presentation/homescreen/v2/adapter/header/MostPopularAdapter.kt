package br.com.rstudio.countries.presentation.homescreen.v2.adapter.header

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.data.model.Country

class MostPopularAdapter : RecyclerView.Adapter<MostPopularViewHolder>() {

  private var countryList: List<Country> = listOf()
  private var onClickListener: ((Country) -> Unit) = { }

  private val firstItem = 0
  private val lastItem = countryList.size - 1

  @SuppressLint("NotifyDataSetChanged")
  fun addItems(countryList: List<Country>, onClickListener: (Country) -> Unit) {
    this.countryList = countryList
    this.onClickListener = onClickListener
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
    return MostPopularViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
    when (position) {
      firstItem -> holder.setMarginStart()
      lastItem -> holder.setMarginEnd()
    }

    holder.bindCountry(countryList[position], onClickListener)
  }

  override fun getItemCount(): Int = countryList.size
}
