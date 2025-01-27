package br.com.rstudio.countries.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import br.com.rstudio.countries.arch.database.AppDatabase
import br.com.rstudio.countries.data.model.genQuizEntity
import br.com.rstudio.countries.testUtil.RxJavaSchedulerRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.test.assertEquals

@SmallTest
@RunWith(AndroidJUnit4::class)
class QuizDaoTest {

  private lateinit var quizDao: QuizDao
  private lateinit var database: AppDatabase

  @get:Rule
  var testSchedulerRule = RxJavaSchedulerRule()

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

    val quiz = quizDao.getAll()

    assertEquals(quizEntity.flag, quiz[0].flag)
  }
}
