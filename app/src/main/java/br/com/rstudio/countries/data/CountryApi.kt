package br.com.rstudio.countries.data

import br.com.rstudio.countries.data.model.CountryDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryApi {

  @GET("all")
  fun getAll(): Observable<List<CountryDTO>>

  @GET("alpha")
  fun getCountryByCode(@Query("codes") codes: List<String>): Observable<List<CountryDTO>>
}
