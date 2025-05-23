package br.com.rstudio.countries.data.datasource

import br.com.rstudio.countries.data.CountryApi
import br.com.rstudio.countries.data.model.Country
import br.com.rstudio.countries.data.model.CountryMapper
import io.reactivex.Observable

class CountryRemoteDataSourceImp(
  private val api: CountryApi,
  private val mapper: CountryMapper
) : CountryDataSource {

  override fun getAll(): Observable<List<Country>> =
    api.getAll().map { mapper.transform(it) }

  override fun getCountryByCode(codes: List<String>): Observable<List<Country>> =
    api.getCountryByCode(codes).map { mapper.transform(it) }
}
