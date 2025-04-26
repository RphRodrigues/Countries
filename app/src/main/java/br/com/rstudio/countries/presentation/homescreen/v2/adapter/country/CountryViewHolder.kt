package br.com.rstudio.countries.presentation.homescreen.v2.adapter.country

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.dpToPx
import br.com.rstudio.countries.arch.extension.inflateView
import br.com.rstudio.countries.arch.extension.updateMarginLayoutParams
import br.com.rstudio.countries.arch.widget.ImageLoaderView
import br.com.rstudio.countries.data.model.Country
import com.google.android.material.card.MaterialCardView

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  private val countryName: AppCompatTextView by lazy { itemView.findViewById(R.id.country_name) }
  private val image: ImageLoaderView by lazy { itemView.findViewById(R.id.country_image_view) }

  private val container: MaterialCardView by lazy {
    itemView.findViewById(R.id.container_item_linear_layout)
  }

  fun updateMarginStart() {
    container.updateMarginLayoutParams {
      marginStart = 10.dpToPx(itemView.context).toInt()
    }
  }

  fun updateMarginEnd() {
    container.updateMarginLayoutParams {
      marginEnd = 10.dpToPx(itemView.context).toInt()
    }
  }

  fun bindCountries(country: Country, onClickListener: (Country) -> Unit) {
    countryName.text = country.name
    image.load(country.flags.pngUrl)

    container.setOnClickListener { onClickListener.invoke(country) }
  }

  companion object {
    fun create(parent: ViewGroup): CountryViewHolder {
      val view = parent.inflateView(R.layout.country_item_v2)
      return CountryViewHolder(view)
    }
  }
}
