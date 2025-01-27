package br.com.rstudio.countries.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Quiz(
  val flag: String,
  val answer: String,
  val question: String,
  val answerOptions: List<String>
) : Parcelable {

  companion object {
    private const val TOTAL_OPTIONS = 4

    fun generateQuiz(countries: List<Country>): Quiz {
      val targetCountry = countries.random()

      val fakeCountry1 = countries.random()
      val fakeCountry2 = countries.random()
      val fakeCountry3 = countries.random()

      val options =
        listOf(targetCountry.name, fakeCountry1.name, fakeCountry2.name, fakeCountry3.name)

      var randomOption: String
      val randomOptions = mutableListOf<String>()

      while (randomOptions.size < TOTAL_OPTIONS) {
        randomOption = options.random()

        if (!randomOptions.contains(randomOption)) {
          randomOptions.add(randomOption)
        }
      }

      return Quiz(
        flag = targetCountry.flags.pngUrl,
        answer = targetCountry.name,
        question = "Qual o nome desse país?",
        answerOptions = randomOptions
      )
    }
  }
}
