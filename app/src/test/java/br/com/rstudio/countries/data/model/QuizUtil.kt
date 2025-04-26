package br.com.rstudio.countries.data.model

import br.com.rstudio.countries.data.entity.QuizEntity

fun genQuizEntity(times: Int = 1): List<QuizEntity> {
  val list = mutableListOf<QuizEntity>()
  repeat(times) { time ->
    list.add(
      QuizEntity(
        flag = "flag $time",
        option1 = "option1 $time",
        option2 = "option2 $time",
        option3 = "option3 $time",
        option4 = "option4 $time",
        question = "question $time",
        correctAnswer = "correctAnswer $time",
        selectedOption = "selectedOption $time",
        isAnswerCorrect = true,
      )
    )
  }
  return list
}

fun genQuiz() = Quiz(
  flag = "quiz flag",
  answer = "quiz answer",
  question = "quiz question",
  answerOptions = listOf("option1", "option2", "option3", "option4")
)
