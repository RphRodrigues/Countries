package br.com.rstudio.countries.data.mapper

import br.com.rstudio.countries.data.entity.QuizEntity
import br.com.rstudio.countries.data.model.Quiz

class QuizMapper {

  fun transform(quizEntity: List<QuizEntity>) = quizEntity.map {
    Quiz(
      flag = it.flag,
      question = it.question,
      answer = it.correctAnswer,
      answerOptions = listOf(it.option1, it.option2, it.option3, it.option4)
    )
  }

  @Suppress("MagicNumber")
  fun transform(quiz: Quiz, isCorrect: Boolean, selectedOption: String) = QuizEntity(
    flag = quiz.flag,
    question = quiz.question,
    correctAnswer = quiz.answer,
    isAnswerCorrect = isCorrect,
    selectedOption = selectedOption,
    option1 = quiz.answerOptions[0],
    option2 = quiz.answerOptions[1],
    option3 = quiz.answerOptions[2],
    option4 = quiz.answerOptions[3]
  )
}
