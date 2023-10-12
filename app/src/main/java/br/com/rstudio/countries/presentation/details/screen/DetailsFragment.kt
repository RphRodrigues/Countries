package br.com.rstudio.countries.presentation.details.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.widget.LoadImageView
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.details.screen.view.DetailsItemListView
import br.com.rstudio.countries.presentation.details.screen.view.DetailsItemView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.text.NumberFormat
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_details), DetailsContract.View {

  private val presenter: DetailsContract.Presenter by inject {
    parametersOf(this)
  }

  private lateinit var imageView: LoadImageView
  private lateinit var containerView: LinearLayoutCompat

  private val country: Country?
    get() = arguments?.getParcelable(COUNTRY_KEY)

  private val borders: List<Country>?
    get() = arguments?.getParcelableArrayList(BORDERS_KEY)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView(view)
  }

  private fun setupView(view: View) {
    imageView = view.findViewById(R.id.details_image_view)
    containerView = view.findViewById(R.id.container_item)
  }

  override fun onStart() {
    super.onStart()
    presenter.onInitializer(country, borders)
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
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindPopulation(population: Int) {
    val locate = Locale.getDefault()
    val populationFormatted = NumberFormat.getIntegerInstance(locate).format(population)

    DetailsItemView(requireContext()).apply {
      bindItem(R.drawable.population_icon, populationFormatted)
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindCapital(capitals: List<String>?) {
    DetailsItemListView(requireContext()).apply {
      bindItem(R.drawable.capital_icon, "Capital")
      bindListItem(capitals)
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindBorders(countryBorders: List<String>?) {
    DetailsItemListView(requireContext()).apply {
      bindItem(R.drawable.border_icon, "Border")
      bindListItem(countryBorders)
    }.also {
      containerView.addView(it)
    }
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