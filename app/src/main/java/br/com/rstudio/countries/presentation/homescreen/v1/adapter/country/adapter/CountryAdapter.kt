package br.com.rstudio.countries.presentation.homescreen.v1.adapter.country.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.data.model.Country

class CountryAdapter : RecyclerView.Adapter<CountryViewHolder>() {

  private var countryList: List<Country> = listOf()
  private var onClickListener: ((Country) -> Unit) = { }

  fun addItems(countryList: List<Country>, onClickListener: (Country) -> Unit) {
    this.countryList = countryList
    this.onClickListener = onClickListener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
    return CountryViewHolder.create(parent)
  }

  override fun getItemCount(): Int = countryList.size

  override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
    holder.bindCountries(countryList[position], onClickListener)
  }
}
