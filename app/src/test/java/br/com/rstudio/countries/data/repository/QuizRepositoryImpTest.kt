package br.com.rstudio.countries.data.repository

import br.com.rstudio.countries.data.datasource.QuizDataSource
import br.com.rstudio.countries.data.model.Quiz
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class QuizRepositoryImpTest {

  private val quiz: Quiz = mockk(relaxed = true)
  private val localDataSource: QuizDataSource = mockk(relaxed = true)
  private val remoteDataSource: QuizDataSource = mockk(relaxed = true)

  private lateinit var repository: QuizRepository

  @Before
  fun setUp() {
    repository = QuizRepositoryImp(localDataSource, remoteDataSource)
  }

  @Test
  fun `when generate quiz is called then should call remote data source`() {
    repository.generateQuiz()

    verify {
      remoteDataSource.generateQuiz()
    }
  }

  @Test
  fun `when get all quizzes is called then should call local data source`() {
    repository.getAllQuizzes()

    verify {
      localDataSource.getAllQuizzes()
    }
  }

  @Test
  fun `when save answer is called then should call local data source`() {
    repository.salveAnswer(quiz, true, "option n")

    verify {
      localDataSource.salveAnswer(quiz, true, "option n")
    }
  }
}
