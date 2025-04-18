package br.com.rstudio.countries.data.datasource

import br.com.rstudio.countries.arch.database.AppDatabase
import br.com.rstudio.countries.data.mapper.QuizMapper
import br.com.rstudio.countries.data.model.Quiz
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizLocalDataSourceImp(
  private val roomDb: AppDatabase,
  private val mapper: QuizMapper = QuizMapper(),
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : QuizDataSource {

  override suspend fun generateQuiz(): Quiz {
    TODO("Not yet implemented")
  }

  override suspend fun getAllQuizzes(): List<Quiz> {
    return withContext(dispatcher) {
      val items = roomDb.quizDao().getAll()
      mapper.transform(items)
    }
  }

  override suspend fun salveAnswer(quiz: Quiz, isCorrect: Boolean, selectedOption: String): Long {
    return withContext(dispatcher) {
      val quizEntity = mapper.transform(quiz, isCorrect, selectedOption)
      roomDb.quizDao().insert(quizEntity)
    }
  }
}
