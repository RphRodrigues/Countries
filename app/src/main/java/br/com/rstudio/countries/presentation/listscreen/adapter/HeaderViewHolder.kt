package br.com.rstudio.countries.presentation.listscreen.adapter

import android.view.View
import android.view.ViewGroup
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.inflateView

class HeaderViewHolder(itemView: View) : ListViewHolder(itemView) {

  companion object {
    fun create(parent: ViewGroup): HeaderViewHolder {
      val view = parent.inflateView(R.layout.header_view_type)
      return HeaderViewHolder(view)
    }
  }
}
