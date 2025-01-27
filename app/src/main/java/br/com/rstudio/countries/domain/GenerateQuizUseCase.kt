package br.com.rstudio.countries.domain

import br.com.rstudio.countries.data.model.Quiz
import br.com.rstudio.countries.data.repository.QuizRepository
import io.reactivex.Observable

class GenerateQuizUseCase(private val quizRepository: QuizRepository) {

  suspend operator fun invoke(): Quiz {
    return quizRepository.generateQuiz()
  }
}
