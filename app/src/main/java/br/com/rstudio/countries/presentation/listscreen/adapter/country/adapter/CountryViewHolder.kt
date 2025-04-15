package br.com.rstudio.countries.presentation.listscreen.adapter.country.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.inflateView
import br.com.rstudio.countries.arch.widget.LoadImageView
import br.com.rstudio.countries.data.model.Country

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  private val countryName: AppCompatTextView by lazy { itemView.findViewById(R.id.country_name) }
  private val image: LoadImageView by lazy { itemView.findViewById(R.id.country_image_view) }

  private val container: LinearLayoutCompat by lazy {
    itemView.findViewById(R.id.container_item_linear_layout)
  }

  fun bindCountries(country: Country, onClickListener: (Country) -> Unit,) {
    countryName.text = country.name
    image.load(country.flags.pngUrl)

    container.setOnClickListener { onClickListener.invoke(country) }
  }

  companion object {
    fun create(parent: ViewGroup): CountryViewHolder {
      val view = parent.inflateView(R.layout.country_item)
      return CountryViewHolder(view)
    }
  }
}
