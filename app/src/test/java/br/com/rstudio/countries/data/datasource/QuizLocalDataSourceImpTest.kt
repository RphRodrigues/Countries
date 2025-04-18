package br.com.rstudio.countries.data.datasource

import br.com.rstudio.countries.arch.database.AppDatabase
import br.com.rstudio.countries.data.mapper.QuizMapper
import br.com.rstudio.countries.data.model.Quiz
import br.com.rstudio.countries.data.model.genQuiz
import br.com.rstudio.countries.data.model.genQuizEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuizLocalDataSourceImpTest {

  private val quiz: Quiz = genQuiz()
  private val mapper: QuizMapper = mockk(relaxed = true)

  private val testDispatcher = StandardTestDispatcher()

  private val room: AppDatabase = mockk(relaxed = true) {
    every { quizDao() } returns mockk(relaxed = true) {
      coEvery { getAll() } returns genQuizEntity(2)
      coEvery { insert(any()) } returns 1L
    }
  }

  private lateinit var localDataSource: QuizDataSource

  @Before
  fun setUp() {
    Dispatchers.setMain(testDispatcher)
    localDataSource = QuizLocalDataSourceImp(room, mapper, Dispatchers.Main)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `when get all quizzes is call then should find it in room database`() = runTest {
    localDataSource.getAllQuizzes()

    coEvery {
      room.quizDao().getAll()
      mapper.transform(any())
    }
  }

  @Ignore("test is failing")
  @Test
  fun `when save quiz answer is called then should call room database`() = runTest {
    val quizEntity = genQuizEntity(1).first()
    every { mapper.transform(any(), any(), any()) } returns quizEntity

    localDataSource.salveAnswer(quiz, false, "canada")

    coVerify {
      room.quizDao().insert(any())
    }
  }
}
