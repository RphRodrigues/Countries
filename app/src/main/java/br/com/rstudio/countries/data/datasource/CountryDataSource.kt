package br.com.rstudio.countries.data.datasource

import br.com.rstudio.countries.data.model.Country
import io.reactivex.Observable

interface CountryDataSource {
  fun getAll(): Observable<List<Country>>
  fun getCountryByCode(codes: List<String>): Observable<List<Country>>
}
