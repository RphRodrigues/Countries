package br.com.rstudio.countries.data.repository

import br.com.rstudio.countries.data.datasource.QuizDataSource
import br.com.rstudio.countries.data.model.Quiz

class QuizRepositoryImp(
  private val localDataSource: QuizDataSource,
  private val remoteDataSource: QuizDataSource
) : QuizRepository {

  override suspend fun generateQuiz(): Quiz {
    return remoteDataSource.generateQuiz()
  }

  override suspend fun getAllQuizzes(): List<Quiz> {
    return localDataSource.getAllQuizzes()
  }

  override suspend fun salveAnswer(quiz: Quiz, isCorrect: Boolean, selectedOption: String): Long {
    return localDataSource.salveAnswer(quiz, isCorrect, selectedOption)
  }
}
