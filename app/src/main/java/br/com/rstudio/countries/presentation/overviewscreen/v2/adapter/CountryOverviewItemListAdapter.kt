package br.com.rstudio.countries.presentation.overviewscreen.v2.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.homescreen.v2.adapter.country.CountryViewHolder

class CountryOverviewItemListAdapter(
  private val borderList: List<Country>,
  private val onclickListener: (Country) -> Unit
) : RecyclerView.Adapter<CountryViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
    return CountryViewHolder.create(parent)
  }

  override fun getItemCount(): Int = borderList.size

  override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
    holder.bindCountries(borderList[position], onclickListener)
  }
}
