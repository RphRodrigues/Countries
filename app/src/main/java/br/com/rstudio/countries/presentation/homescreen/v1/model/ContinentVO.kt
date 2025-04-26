package br.com.rstudio.countries.presentation.homescreen.v1.model

import br.com.rstudio.countries.data.model.Country

class ContinentVO(
  val name: String,
  val countries: List<Country>,
  val onClickListener: (Country) -> Unit
)

class CountryVO(
  val name: String,
  val flagURL: String
)
