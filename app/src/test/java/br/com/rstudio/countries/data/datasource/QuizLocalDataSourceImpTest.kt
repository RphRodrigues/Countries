package br.com.rstudio.countries.data.datasource

import br.com.rstudio.countries.arch.database.AppDatabase
import br.com.rstudio.countries.data.dao.QuizDao
import br.com.rstudio.countries.data.mapper.QuizMapper
import br.com.rstudio.countries.data.model.Quiz
import br.com.rstudio.countries.data.model.genQuiz
import br.com.rstudio.countries.data.model.genQuizEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class QuizLocalDataSourceImpTest {

  private val quiz: Quiz = genQuiz()
  private val mapper: QuizMapper = mockk(relaxed = true)
  private val room: AppDatabase = mockk(relaxed = true)

  private lateinit var localDataSource: QuizDataSource

  @Before
  fun setUp() {
    localDataSource = QuizLocalDataSourceImp(room, mapper)
  }

  @Test
  fun `when get all quizzes is call then should find it in room database`() {
    val quizDao = mockk<QuizDao>(relaxed = true) {
      every { getAll() } returns genQuizEntity(2)
    }
    every { room.quizDao() } returns quizDao

    localDataSource.getAllQuizzes()

    verify {
      room.quizDao().getAll()
      mapper.transform(any())
    }
  }

  @Test
  fun `when save quiz answer is called then should call room database`() {
    every { room.quizDao() } returns mockk(relaxed = true) {
      every { insert(any()) } returns 1
    }
    every { mapper.transform(quiz, false, "canada") } returns genQuizEntity(1).first()

    localDataSource.salveAnswer(quiz, false, "canada")

    verify {
      room.quizDao().insert(any())
    }
  }
}
