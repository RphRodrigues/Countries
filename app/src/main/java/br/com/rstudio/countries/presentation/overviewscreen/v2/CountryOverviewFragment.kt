package br.com.rstudio.countries.presentation.overviewscreen.v2

import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.dpToPx
import br.com.rstudio.countries.arch.extension.getBottomLeftColor
import br.com.rstudio.countries.arch.extension.getBottomRightColor
import br.com.rstudio.countries.arch.extension.hideError
import br.com.rstudio.countries.arch.extension.hideLoader
import br.com.rstudio.countries.arch.extension.popBackStack
import br.com.rstudio.countries.arch.extension.replaceFragment
import br.com.rstudio.countries.arch.extension.setupBackPressedCallback
import br.com.rstudio.countries.arch.extension.showError
import br.com.rstudio.countries.arch.extension.showLoader
import br.com.rstudio.countries.arch.model.ErrorModel
import br.com.rstudio.countries.arch.widget.ImageLoaderView
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.presentation.overviewscreen.v2.view.CountryOverviewHeaderView
import br.com.rstudio.countries.presentation.overviewscreen.v2.view.CountryOverviewItemListView
import br.com.rstudio.countries.presentation.overviewscreen.v2.view.CountryOverviewItemView
import java.text.NumberFormat
import java.util.Locale
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

@Suppress("TooManyFunctions")
class CountryOverviewFragment : Fragment(R.layout.fragment_shared), CountryOverviewContract.View {

  private val presenter: CountryOverviewContract.Presenter by inject {
    parametersOf(this)
  }

  private lateinit var imageView: ImageLoaderView
  private lateinit var leftColorView: View
  private lateinit var rightColorView: View
  private lateinit var containerView: LinearLayoutCompat

  private val country: Country?
    get() = arguments?.getParcelable(COUNTRY_KEY)

  private val countryCode: String?
    get() = arguments?.getString(CODE_KEY)

  var onResumeCallback: (() -> Unit)? = null

  private val callback = {
    presenter.onBackPressed()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView(view)
    setupBackPressedCallback(callback)
  }

  private fun setupView(view: View) {
    imageView = view.findViewById(R.id.fragment_flag_image_view)
    leftColorView = view.findViewById(R.id.fragment_left_flag_color_view)
    rightColorView = view.findViewById(R.id.fragment_right_flag_color_view)
    containerView = view.findViewById(R.id.fragment_container_view)

    containerView.apply {
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
    if (countryCode != null) {
      presenter.onFetchData(countryCode)
    } else {
      presenter.onInitializer(country)
    }
  }

  override fun onResume() {
    super.onResume()
    presenter.onResume()
    onResumeCallback?.invoke()
  }

  override fun clearViewContent() {
    containerView.removeAllViews()
  }

  override fun bindCountry(country: Country, countryBorders: List<Country>?) {
    bindCountryImage(country)
    bindName(country.name)
    bindPopulation(country.population)
    bindCapital(country.capital)
    bindContinent(country.continent)
    bindNumberBorders(countryBorders)
    bindBorders(countryBorders)
  }

  private fun bindCountryImage(country: Country) {
    imageView.load(country.flags.pngUrl) { drawable ->
      leftColorView.setBackgroundColor(drawable.getBottomLeftColor())
      rightColorView.setBackgroundColor(drawable.getBottomRightColor())
    }
  }

  private fun bindName(countryName: String) {
    CountryOverviewHeaderView(requireContext()).apply {
      bindTitle(countryName)
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindPopulation(population: Int) {
    val locate = Locale.getDefault()
    val populationFormatted = NumberFormat.getIntegerInstance(locate).format(population)

    CountryOverviewItemView(requireContext()).apply {
      bindItem(context.getString(R.string.population), populationFormatted)
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindCapital(capitals: List<String>?) {
    if (capitals == null) return

    CountryOverviewItemListView(requireContext()).apply {
      bindItem(context.getString(R.string.capital), capitals.joinToString(", "))
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindContinent(continent: String) {
    CountryOverviewItemView(requireContext()).apply {
      bindItem(context.getString(R.string.continent), continent)
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindNumberBorders(countryBorders: List<Country>?) {
    if (countryBorders.isNullOrEmpty()) return

    CountryOverviewItemView(requireContext()).apply {
      bindItem(context.getString(R.string.number_of_borders), countryBorders.size.toString())
    }.also {
      containerView.addView(it)
    }
  }

  private fun bindBorders(countryBorders: List<Country>?) {
    if (countryBorders.isNullOrEmpty()) return

    CountryOverviewItemListView(requireContext()).apply {
      bindListItem(countryBorders, presenter::onCountryClicked)
    }.also {
      containerView.addView(it)
    }
  }

  override fun openCountryOverviewScreen(country: Country) {
    val fragment = CountryOverviewFragment.newInstance(country)
    activity.replaceFragment(fragment)
  }

  override fun finish() {
    activity.popBackStack()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  override fun showError(err: ErrorModel) {
    activity.showError(err) {
      presenter.onFetchData(countryCode)
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

  companion object {
    private const val CODE_KEY = "code"
    private const val COUNTRY_KEY = "country"

    fun newInstance(country: Country) = CountryOverviewFragment().apply {
      arguments = bundleOf(COUNTRY_KEY to country)
    }

    fun newInstance(countryCode: String) = CountryOverviewFragment().apply {
      arguments = bundleOf(CODE_KEY to countryCode)
    }
  }
}
