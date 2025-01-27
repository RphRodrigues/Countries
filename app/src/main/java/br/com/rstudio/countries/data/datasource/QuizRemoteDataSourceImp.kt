package br.com.rstudio.countries.data.datasource

import br.com.rstudio.countries.data.model.Quiz
import br.com.rstudio.countries.data.repository.CountryRepository
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizRemoteDataSourceImp(
  private val countryRepository: CountryRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : QuizDataSource {

  override suspend fun generateQuiz(): Quiz {
    return withContext(dispatcher) {
      val countries = countryRepository.getAll().blockingFirst()
      Quiz.generateQuiz(countries)
    }
  }

  override suspend fun getAllQuizzes(): List<Quiz> {
    TODO("Not yet implemented")
  }

  override suspend fun salveAnswer(quiz: Quiz, isCorrect: Boolean, selectedOption: String): Long {
    TODO("Not yet implemented")
  }
}
