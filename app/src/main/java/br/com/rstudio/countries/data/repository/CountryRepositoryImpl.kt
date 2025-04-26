package br.com.rstudio.countries.data.repository

import br.com.rstudio.countries.data.datasource.CountryDataSource
import br.com.rstudio.countries.data.model.Country
import io.reactivex.Observable

class CountryRepositoryImpl(private val remoteDataSource: CountryDataSource) : CountryRepository {

  override fun getAll(): Observable<List<Country>> {
    return remoteDataSource.getAll()
  }

  override fun getCountryByCode(codes: List<String>): Observable<List<Country>> {
    return remoteDataSource.getCountryByCode(codes)
  }
}
