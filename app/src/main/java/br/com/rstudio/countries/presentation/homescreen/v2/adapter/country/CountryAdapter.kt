package br.com.rstudio.countries.presentation.homescreen.v2.adapter.country

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.data.model.Country

class CountryAdapter : RecyclerView.Adapter<CountryViewHolder>() {

  private var countryList: List<Country> = listOf()
  private var onClickListener: ((Country) -> Unit) = { }

  private val firstItem = 0
  private val lastItem = countryList.size - 1

  fun addItems(countryList: List<Country>, onClickListener: (Country) -> Unit) {
    this.countryList = countryList
    this.onClickListener = onClickListener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
    return CountryViewHolder.create(parent)
  }

  override fun getItemCount(): Int = countryList.size

  override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
    when (position) {
      firstItem -> holder.updateMarginStart()
      lastItem -> holder.updateMarginEnd()
    }

    holder.bindCountries(countryList[position], onClickListener)
  }
}
