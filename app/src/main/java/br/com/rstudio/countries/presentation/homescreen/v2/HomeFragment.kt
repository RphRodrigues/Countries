package br.com.rstudio.countries.presentation.homescreen.v2

import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.dpToPx
import br.com.rstudio.countries.arch.extension.hideError
import br.com.rstudio.countries.arch.extension.hideLoader
import br.com.rstudio.countries.arch.extension.replaceFragment
import br.com.rstudio.countries.arch.extension.setupBackPressedCallback
import br.com.rstudio.countries.arch.extension.showError
import br.com.rstudio.countries.arch.extension.showLoader
import br.com.rstudio.countries.arch.model.ErrorModel
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.details.screen.DetailsFragment
import br.com.rstudio.countries.presentation.homescreen.v1.model.ContinentVO
import br.com.rstudio.countries.presentation.homescreen.v2.adapter.MainAdapter
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

@Suppress("TooManyFunctions")
class HomeFragment : Fragment(R.layout.fragment_home), HomeContract.View {

  private val presenter: HomeContract.Presenter by inject {
    parametersOf(this)
  }

  private lateinit var recyclerView: RecyclerView

  private val callback = {
    presenter.onBackPressed()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView(view)
    setupBackPressedCallback(callback)
  }

  private fun setupView(view: View) {
    recyclerView = view.findViewById(R.id.main_recycler_view)

    recyclerView.apply {
      adapter = MainAdapter()
      setHasFixedSize(true)

      clipToOutline = true
      outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
          view?.let {
            outline?.setRoundRect(0, 0, view.width, view.height, 30.dpToPx(requireContext()))
          }
        }
      }
    }
  }

  override fun onStart() {
    super.onStart()
    presenter.onInitialize()
  }

  override fun onResume() {
    super.onResume()
    presenter.onResume()
  }

  override fun bindData(mostPopularCountries: List<Country>, continentList: List<ContinentVO>) {
    (recyclerView.adapter as? MainAdapter)?.run {
      addItems(mostPopularCountries, continentList, presenter::onCountryClickListener)
    }
  }

  override fun openDetailsScreen(country: Country, borders: List<Country>?) {
    val fragment = DetailsFragment.newInstance(country, borders)
    activity.replaceFragment(fragment)
  }

  override fun finish() {
    activity?.finish()
  }

  override fun showError(err: ErrorModel) {
    activity.showError(err) {
      presenter.onInitialize()
    }
  }

  override fun hideError() {
    activity.hideError()
  }

  override fun showLoader() {
    activity.showLoader()
  }

  override fun hideLoader() {
    activity.hideLoader()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  companion object {
    fun newInstance() = HomeFragment()
  }
}
