package br.com.rstudio.countries.presentation.rankingscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.goToBottomNavigationHome
import br.com.rstudio.countries.arch.extension.setupBackPressedCallback

class RankingFragment : Fragment(R.layout.fragment_ranking) {

  private val callback = {
    activity.goToBottomNavigationHome()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupBackPressedCallback(callback)
  }

  companion object {
    fun newInstance() = RankingFragment()
  }
}
