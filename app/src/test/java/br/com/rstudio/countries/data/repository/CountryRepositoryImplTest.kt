package br.com.rstudio.countries.data.repository

import br.com.rstudio.countries.data.datasource.CountryDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CountryRepositoryImplTest {

  private lateinit var repository: CountryRepository
  private val remoteDataSource: CountryDataSource = mockk(relaxed = true)

  @Before
  fun setUp() {
    repository = CountryRepositoryImpl(remoteDataSource)
  }

  @Test
  fun `when get all is called then should call remote data source`() {
    repository.getAll()

    verify {
      remoteDataSource.getAll()
    }
  }

  @Test
  fun `when get country by code is called then should call remote data source`() {
    val codes = listOf("BR", "AR")
    repository.getCountryByCode(codes)

    verify {
      remoteDataSource.getCountryByCode(codes)
    }
  }
}
