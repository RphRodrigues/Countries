package br.com.rstudio.countries.presentation.profilescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.goToBottomNavigationHome
import br.com.rstudio.countries.arch.extension.setupBackPressedCallback

class ProfileFragment : Fragment(R.layout.fragment_profile) {

  private val callback = {
    activity.goToBottomNavigationHome()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupBackPressedCallback(callback)
  }

  companion object {
    fun newInstance() = ProfileFragment()
  }
}
