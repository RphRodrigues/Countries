package br.com.rstudio.countries.domain

import br.com.rstudio.countries.data.model.Quiz
import br.com.rstudio.countries.data.repository.QuizRepository
import br.com.rstudio.countries.testUtil.coVerifyOnce
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveQuizAnsweredUseCaseTest {

  private val quiz: Quiz = mockk(relaxed = true)
  private val quizRepository: QuizRepository = mockk(relaxed = true)

  private lateinit var saveQuizAnsweredUseCase: SaveQuizAnsweredUseCase

  @Before
  fun setup() {
    saveQuizAnsweredUseCase = SaveQuizAnsweredUseCase(quizRepository)
  }

  @Test
  fun `when save quiz answered use case with correct answer is invoked then should call quiz repository`() = runTest {
    saveQuizAnsweredUseCase(quiz, true, "option 1")

    coVerifyOnce {
      quizRepository.salveAnswer(quiz, true, "option 1")
    }
  }

  @Test
  fun `when save quiz answered use case with correct wrong is invoked then should call quiz repository`() = runTest {
    saveQuizAnsweredUseCase(quiz, false, "option 1")

    coVerifyOnce {
      quizRepository.salveAnswer(quiz, false, "option 1")
    }
  }
}
