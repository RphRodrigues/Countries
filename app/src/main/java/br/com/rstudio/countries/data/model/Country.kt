package br.com.rstudio.countries.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Country(
  val countryName: CountryName,
  val capital: List<String>?,
  val borders: List<String>?,
  val timezones: List<String>,
  val continents: List<String>,
  val languages: CountryLanguage?,
  val littleFlag: String,
  val code: String,
  val independent: Boolean,
  val population: Int,
  val flags: CountryFlag,
) : Parcelable {

  val name: String
    get() = countryName.common

  val continent: String
    get() = continents.firstOrNull() ?: "undefined"
}

@Parcelize
class CountryName(
  val common: String,
  val official: String,
) : Parcelable

@Parcelize
class CountryLanguage(
  val ara: String? = null,
  val bre: String? = null,
  val ces: String? = null,
  val cym: String? = null,
  val deu: String? = null,
  val est: String? = null,
  val fin: String? = null,
  val fra: String? = null,
  val hrv: String? = null,
  val hun: String? = null,
  val ita: String? = null,
  val jpn: String? = null,
  val kor: String? = null,
  val nld: String? = null,
  val per: String? = null,
  val pol: String? = null,
  val portuguese: String? = null,
  val rus: String? = null,
  val slk: String? = null,
  val spa: String? = null,
  val srp: String? = null,
  val swe: String? = null,
  val tur: String? = null,
  val urd: String? = null,
  val zho: String? = null
) : Parcelable

@Parcelize
class CountryFlag(
  val pngUrl: String,
  val svgUrl: String,
  val alt: String?
) : Parcelable
