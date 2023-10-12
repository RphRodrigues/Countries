package br.com.rstudio.countries.data.model

class CountryMapper {

  fun transform(dto: List<CountryDTO>) = dto.map { transform(it) }

  private fun transform(dto: CountryDTO) = Country(
    countryName = transform(dto.name),
    capital = dto.capital,
    borders = dto.borders,
    timezones = dto.timezones,
    continents = dto.continents,
    languages = dto.languages?.let { transform(it) },
    littleFlag = dto.flag,
    population = dto.population,
    flags = transform(dto.flags),
    code = dto.cca3,
    independent = dto.independent
  )

  private fun transform(dto: CountryNameDTO) = CountryName(
    common = dto.common,
    official = dto.official
  )

  private fun transform(dto: CountryLanguageDTO) = CountryLanguage(
    portuguese = dto.por
  )

  private fun transform(dto: CountryFlagDTO) = CountryFlag(
    pngUrl = dto.png,
    svgUrl = dto.svg,
    alt = dto.alt,
  )
}