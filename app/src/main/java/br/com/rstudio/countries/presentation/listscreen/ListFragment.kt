package br.com.rstudio.countries.presentation.listscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.*
import br.com.rstudio.countries.arch.model.ErrorModel
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.details.screen.DetailsFragment
import br.com.rstudio.countries.presentation.listscreen.adapter.MainAdapter
import br.com.rstudio.countries.presentation.listscreen.model.ContinentVO
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ListFragment : Fragment(R.layout.fragment_list), ListContract.View {

  private val presenter: ListContract.Presenter by inject {
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
    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = MainAdapter()
  }

  override fun onStart() {
    super.onStart()
    presenter.onInitialize()
  }

  override fun bindContinents(continentList: List<ContinentVO>) {
    (recyclerView.adapter as? MainAdapter)?.addItems(continentList)
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
    fun newInstance() = ListFragment()
  }
}
