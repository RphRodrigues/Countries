package br.com.rstudio.countries

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import br.com.rstudio.countries.arch.database.AppDatabase
import br.com.rstudio.countries.data.dao.QuizDao
import br.com.rstudio.countries.data.entity.QuizEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.test.assertEquals

@SmallTest
@RunWith(AndroidJUnit4::class)
class QuizDaoTest {

  private lateinit var quizDao: QuizDao
  private lateinit var database: AppDatabase

//  @get:Rule
//  var testSchedulerRule = RxJavaSchedulerRule()

  @Before
  fun setup() {
    database = Room.inMemoryDatabaseBuilder(
      ApplicationProvider.getApplicationContext(),
      AppDatabase::class.java
    ).allowMainThreadQueries().build()

    quizDao = database.quizDao()
  }

  @After
  @Throws(IOException::class)
  fun close() {
    database.close()
  }

  @Test
  @Throws(Exception::class)
  fun writeQuizAndReadInList() {
    val quizEntity = genQuizEntity().first()
    quizDao.insert(quizEntity)

    val quiz = quizDao.getAll().blockingGet()

    assertEquals(quizEntity.flag, quiz[0].flag)
  }
}

fun genQuizEntity(times: Int = 1): List<QuizEntity> {
  return (0 until times).map { time ->
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
  }
}