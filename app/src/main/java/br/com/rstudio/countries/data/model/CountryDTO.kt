package br.com.rstudio.countries.data.model

import com.google.gson.annotations.SerializedName

class CountryDTO(

  @SerializedName("name")
  val name: CountryNameDTO,

  @SerializedName("capital")
  val capital: List<String>?,

  @SerializedName("borders")
  val borders: List<String>?,

  @SerializedName("timezones")
  val timezones: List<String>,

  @SerializedName("continents")
  val continents: List<String>,

  @SerializedName("flag")
  val flag: String,

  @SerializedName("cca3")
  val cca3: String,

  @SerializedName("population")
  val population: Int,

  @SerializedName("flags")
  val flags: CountryFlagDTO,

  @SerializedName("languages")
  val languages: CountryLanguageDTO?,

  @SerializedName("independent")
  val independent: Boolean
)

class CountryNameDTO(
  @SerializedName("common")
  val common: String,

  @SerializedName("official")
  val official: String,
)

class CountryFlagDTO(
  @SerializedName("png")
  val png: String,

  @SerializedName("svg")
  val svg: String,

  @SerializedName("alt")
  val alt: String?
)

class CountryLanguageDTO(
  @SerializedName("ara") val ara: String?,
  @SerializedName("bre") val bre: String?,
  @SerializedName("ces") val ces: String?,
  @SerializedName("cym") val cym: String?,
  @SerializedName("deu") val deu: String?,
  @SerializedName("est") val est: String?,
  @SerializedName("fin") val fin: String?,
  @SerializedName("fra") val fra: String?,
  @SerializedName("hrv") val hrv: String?,
  @SerializedName("hun") val hun: String?,
  @SerializedName("ita") val ita: String?,
  @SerializedName("jpn") val jpn: String?,
  @SerializedName("kor") val kor: String?,
  @SerializedName("nld") val nld: String?,
  @SerializedName("per") val per: String?,
  @SerializedName("pol") val pol: String?,
  @SerializedName("por") val por: String?,
  @SerializedName("rus") val rus: String?,
  @SerializedName("slk") val slk: String?,
  @SerializedName("spa") val spa: String?,
  @SerializedName("srp") val srp: String?,
  @SerializedName("swe") val swe: String?,
  @SerializedName("tur") val tur: String?,
  @SerializedName("urd") val urd: String?,
  @SerializedName("zho") val zho: String?
)