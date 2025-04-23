package br.com.rstudio.countries.presentation.homescreen.v2.adapter.header

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.dpToPx
import br.com.rstudio.countries.arch.extension.inflateView
import br.com.rstudio.countries.arch.extension.updateMarginLayoutParams
import br.com.rstudio.countries.arch.widget.ImageLoaderView
import br.com.rstudio.countries.data.model.Country

class MostPopularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  private val imageView: ImageLoaderView by lazy { itemView.findViewById(R.id.most_popular_country_image_view) }
  private val container: ConstraintLayout by lazy { itemView.findViewById(R.id.most_popular_country_item_container) }

  fun setMarginStart() {
    container.updateMarginLayoutParams {
      marginStart = 10.dpToPx(itemView.context).toInt()
    }
  }

  fun setMarginEnd() {
    container.updateMarginLayoutParams {
      marginEnd = 10.dpToPx(itemView.context).toInt()
    }
  }

  fun bindCountry(country: Country, onClickListener: (Country) -> Unit) {
    imageView.load(country.flags.pngUrl)

    container.setOnClickListener { onClickListener.invoke(country) }
  }

  companion object {
    fun create(parent: ViewGroup): MostPopularViewHolder {
      val view = parent.inflateView(R.layout.most_popular_country_item)
      return MostPopularViewHolder(view)
    }
  }
}
