package br.com.rstudio.countries.data.mapper

import br.com.rstudio.countries.data.entity.QuizEntity
import br.com.rstudio.countries.data.model.genQuiz
import br.com.rstudio.countries.data.model.genQuizEntity
import org.junit.Test
import kotlin.test.assertEquals

class QuizMapperTest {

  private val mapper: QuizMapper = QuizMapper()

  @Test
  fun transform() {
    val quizList = mapper.transform(genQuizEntity())

    assertEquals("flag 0", quizList[0].flag)
    assertEquals("correctAnswer 0", quizList[0].answer)
    assertEquals("question 0", quizList[0].question)
    assertEquals("option1 0", quizList[0].answerOptions[0])
    assertEquals("option2 0", quizList[0].answerOptions[1])
    assertEquals("option3 0", quizList[0].answerOptions[2])
    assertEquals("option4 0", quizList[0].answerOptions[3])
  }

  @Test
  fun testTransform() {
    val quizEntity: QuizEntity = mapper.transform(genQuiz(), true, "option2")

    assertEquals("quiz flag", quizEntity.flag)
    assertEquals("option1", quizEntity.option1)
    assertEquals("option2", quizEntity.option2)
    assertEquals("option3", quizEntity.option3)
    assertEquals("option4", quizEntity.option4)
    assertEquals(true, quizEntity.isAnswerCorrect)
    assertEquals("option2", quizEntity.selectedOption)
    assertEquals("quiz answer", quizEntity.correctAnswer)
  }
}
