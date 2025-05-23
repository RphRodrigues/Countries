package br.com.rstudio.countries.presentation.overviewscreen.v1

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.popBackStack
import br.com.rstudio.countries.arch.extension.setupBackPressedCallback
import br.com.rstudio.countries.arch.widget.ImageLoaderView
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.overviewscreen.v1.view.DetailsItemListView
import br.com.rstudio.countries.presentation.overviewscreen.v1.view.DetailsItemView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.text.NumberFormat
import java.util.Locale

@Suppress("TooManyFunctions")
class DetailsFragment : Fragment(R.layout.fragment_details), DetailsContract.View {

  private val presenter: DetailsContract.Presenter by inject {
    parametersOf(this)
  }

  private lateinit var imageView: ImageLoaderView
  private lateinit var containerView: LinearLayoutCompat

  private val country: Country?
    get() = arguments?.getParcelable(COUNTRY_KEY)

  private val borders: List<Country>?
    get() = arguments?.getParcelableArrayList(BORDERS_KEY)

  private val callback = {
    presenter.onBackPressed()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView(view)
    setupBackPressedCallback(callback)
  }

  private fun setupView(view: View) {
    imageView = view.findViewById(R.id.details_image_view)
    containerView = view.findViewById(R.id.container_item)
  }

  override fun onStart() {
    super.onStart()
    presenter.onInitializer(country, borders)
  }

  override fun onResume() {
    super.onResume()
    presenter.onResume()
  }

  override fun clearViewContent() {
    containerView.removeAllViews()
  }

  override fun bindCountry(country: Country, countryBorders: List<String>?) {
    bindCountryImage(country)
    bindName(country.name)
    bindPopulation(country.population)
    bindCapital(country.capital)
    bindBorders(countryBorders)
  }

  private fun bindCountryImage(country: Country) {
    imageView.load(country.flags.pngUrl)
  }

  private fun bindName(countryName: String) {
    DetailsItemView(requireContext()).apply {
      bindItem(R.drawable.flag_icon, countryName)
      bindTag("flag_icon")
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindPopulation(population: Int) {
    val locate = Locale.getDefault()
    val populationFormatted = NumberFormat.getIntegerInstance(locate).format(population)

    DetailsItemView(requireContext()).apply {
      bindItem(R.drawable.population_icon, populationFormatted)
      bindTag("population_icon")
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindCapital(capitals: List<String>?) {
    DetailsItemListView(requireContext()).apply {
      bindItem(R.drawable.capital_icon, "Capital")
      bindListItem(capitals)
      bindTag("capital_icon")
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindBorders(countryBorders: List<String>?) {
    DetailsItemListView(requireContext()).apply {
      bindItem(R.drawable.border_icon, "Border")
      bindListItem(countryBorders)
      bindTag("border_icon")
    }.also {
      containerView.addView(it)
    }
  }

  override fun finish() {
    activity.popBackStack()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  companion object {
    private const val COUNTRY_KEY = "country"
    private const val BORDERS_KEY = "borders"

    fun newInstance(country: Country, borders: List<Country>?) = DetailsFragment().apply {
      arguments = bundleOf(COUNTRY_KEY to country, BORDERS_KEY to borders)
    }
  }
}
