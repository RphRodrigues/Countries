package br.com.rstudio.countries.presentation

import br.com.rstudio.countries.data.model.Country
import io.mockk.every
import io.mockk.mockk

object CountryModel {

  val countryList = listOf<Country>(
    mockk(relaxed = true) {
      every { code } returns "1"
      every { continent } returns "Antarctica"
      every { name } returns "Country 1"
      every { littleFlag } returns "litter flag 1"
      every { borders } returns listOf("3")
    },

    mockk(relaxed = true) {
      every { code } returns "2"
      every { continent } returns "South America"
      every { name } returns "Country 2"
      every { littleFlag } returns "litter flag 2"
    },

    mockk(relaxed = true) {
      every { code } returns "3"
      every { continent } returns "South America"
      every { name } returns "Country 3"
      every { littleFlag } returns "litter flag 3"
    }
  )

  val countryDetail = mockk<Country>(relaxed = true) {
    every { name } returns "Brazil"
    every { population } returns 212559409
    every { capital } returns listOf("Brasília")
    every { continent } returns "South America"
    every { borders } returns listOf("Ar, Ur, Co")
  }

  val countryBorders = arrayListOf<Country>(
    mockk(relaxed = true) {
      every { code } returns "3"
      every { name } returns "Argentina"
      every { littleFlag } returns "🇦🇷"
    },

    mockk(relaxed = true) {
      every { code } returns "4"
      every { name } returns "Uruguay"
      every { littleFlag } returns "🇺🇾"
    },

    mockk(relaxed = true) {
      every { code } returns "5"
      every { name } returns "Colombia"
      every { littleFlag } returns "🇨🇴"
    }
  )
}
