package br.com.rstudio.countries.domain

import br.com.rstudio.countries.data.model.Quiz
import br.com.rstudio.countries.data.repository.QuizRepository

class SaveQuizAnsweredUseCase(private val quizRepository: QuizRepository) {

  suspend operator fun invoke(quiz: Quiz, isCorrect: Boolean, selectedOption: String): Long {
    return quizRepository.salveAnswer(quiz, isCorrect, selectedOption)
  }
}
