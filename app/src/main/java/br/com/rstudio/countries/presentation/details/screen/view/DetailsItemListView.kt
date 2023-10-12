package br.com.rstudio.countries.presentation.details.screen.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.presentation.details.screen.adapter.DetailsItemListAdapter

class DetailsItemListView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val detailsItemView: DetailsItemView by lazy { findViewById(R.id.details_item_view) }
  private val recyclerView: RecyclerView by lazy { findViewById(R.id.details_border_recycler_view) }

  init {
    LayoutInflater.from(context).inflate(R.layout.details_list_item, this, true)
  }

  fun bindItem(@DrawableRes icon: Int?, text: String) {
    detailsItemView.bindItem(icon, text)
  }

  fun bindListItem(items: List<String>?) {
    if (items.isNullOrEmpty()) {
      detailsItemView.isVisible = false
      recyclerView.isVisible = false
    } else {
      recyclerView.adapter = DetailsItemListAdapter(items)
    }
  }
}
