package br.com.rstudio.countries.data.repository

import br.com.rstudio.countries.data.model.Country
import io.reactivex.Observable

interface CountryRepository {
  fun getAll(): Observable<List<Country>>
  fun getCountryByCode(codes: List<String>): Observable<List<Country>>
}