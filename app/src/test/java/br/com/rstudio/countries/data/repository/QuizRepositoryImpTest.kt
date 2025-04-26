package br.com.rstudio.countries.data.repository

import br.com.rstudio.countries.data.datasource.QuizDataSource
import br.com.rstudio.countries.data.model.Quiz
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
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
  fun `when generate quiz is called then should call remote data source`() = runTest {
    repository.generateQuiz()

    coVerify {
      remoteDataSource.generateQuiz()
    }
  }

  @Test
  fun `when get all quizzes is called then should call local data source`() = runTest {
    repository.getAllQuizzes()

    coVerify {
      localDataSource.getAllQuizzes()
    }
  }

  @Test
  fun `when save answer is called then should call local data source`() = runTest {
    repository.salveAnswer(quiz, true, "option n")

    coVerify {
      localDataSource.salveAnswer(quiz, true, "option n")
    }
  }
}
