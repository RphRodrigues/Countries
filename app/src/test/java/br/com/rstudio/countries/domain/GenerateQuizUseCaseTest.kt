package br.com.rstudio.countries.domain

import br.com.rstudio.countries.data.repository.QuizRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GenerateQuizUseCaseTest {

  private lateinit var generateQuizUseCase: GenerateQuizUseCase
  private val quizRepository: QuizRepository = mockk(relaxed = true)

  @Before
  fun setup() {
    generateQuizUseCase = GenerateQuizUseCase(quizRepository)
  }

  @Test
  fun `when generate quiz use case is invoked then should call quiz repository`() = runTest {
    generateQuizUseCase()

    coVerify(exactly = 1) {
      quizRepository.generateQuiz()
    }
  }
}
