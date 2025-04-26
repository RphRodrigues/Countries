package br.com.rstudio.countries.data.repository

import br.com.rstudio.countries.data.model.Quiz

interface QuizRepository {
  suspend fun generateQuiz(): Quiz
  suspend fun getAllQuizzes(): List<Quiz>
  suspend fun salveAnswer(quiz: Quiz, isCorrect: Boolean, selectedOption: String): Long
}
