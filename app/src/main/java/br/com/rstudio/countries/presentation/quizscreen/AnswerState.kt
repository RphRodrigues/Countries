package br.com.rstudio.countries.presentation.quizscreen

sealed class AnswerState {
  object Correct : AnswerState()
  object Wrong : AnswerState()
  object Initial : AnswerState()
}
