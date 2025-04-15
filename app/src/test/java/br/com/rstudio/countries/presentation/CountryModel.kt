package br.com.rstudio.countries.presentation

import br.com.rstudio.countries.data.model.Country
import io.mockk.every
import io.mockk.mockk

object CountryModel {

  val countryList = listOf<Country>(
    mockk(relaxed = true) {
      every { continent } returns "Antarctica"
      every { name } returns "Country 1"
      every { littleFlag } returns "litter flag 1"
    },

    mockk(relaxed = true) {
      every { continent } returns "South America"
      every { name } returns "Country 2"
      every { littleFlag } returns "litter flag 2"
    },

    mockk(relaxed = true) {
      every { continent } returns "South America"
      every { name } returns "Country 3"
      every { littleFlag } returns "litter flag 3"
    }
  )

  val countryDetail = mockk<Country>(relaxed = true) {
    every { name } returns "Brazil"
    every { population } returns 212559409
    every { capital } returns listOf("Brasília")
  }

  val countryBorders = arrayListOf<Country>(
    mockk(relaxed = true) {
      every { name } returns "Argentina"
      every { littleFlag } returns "🇦🇷"
    },

    mockk(relaxed = true) {
      every { name } returns "Uruguay"
      every { littleFlag } returns "🇺🇾"
    },

    mockk(relaxed = true) {
      every { name } returns "Colombia"
      every { littleFlag } returns "🇨🇴"
    }
  )
}
