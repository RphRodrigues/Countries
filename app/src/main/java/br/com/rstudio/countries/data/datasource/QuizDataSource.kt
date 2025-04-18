package br.com.rstudio.countries.data.datasource

import br.com.rstudio.countries.data.model.Quiz

interface QuizDataSource {
  suspend fun generateQuiz(): Quiz
  suspend fun getAllQuizzes(): List<Quiz>
  suspend fun salveAnswer(quiz: Quiz, isCorrect: Boolean, selectedOption: String): Long
}
