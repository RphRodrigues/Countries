package br.com.rstudio.countries.presentation.overviewscreen.v2.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.overviewscreen.v2.adapter.CountryOverviewItemListAdapter

class CountryOverviewItemListView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val recyclerView: RecyclerView by lazy { findViewById(R.id.overview_border_recycler_view) }
  private val borderTitleView: AppCompatTextView by lazy { findViewById(R.id.overview_border_title) }
  private val countryOverviewItemView: CountryOverviewItemView by lazy { findViewById(R.id.overview_item_view) }

  init {
    LayoutInflater.from(context).inflate(R.layout.overview_list_item, this, true)
  }

  fun bindItem(item: String, text: String) {
    countryOverviewItemView.bindItem(item, text)
  }

  fun bindListItem(items: List<Country>?, onClickListener: (Country) -> Unit) {
    if (items.isNullOrEmpty()) {
      countryOverviewItemView.isVisible = false
      recyclerView.isVisible = false
    } else {
      countryOverviewItemView.isVisible = false
      borderTitleView.isVisible = true
      recyclerView.adapter = CountryOverviewItemListAdapter(items, onClickListener)
    }
  }
}
