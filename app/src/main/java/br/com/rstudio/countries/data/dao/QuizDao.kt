package br.com.rstudio.countries.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.rstudio.countries.data.entity.QuizEntity

@Dao
interface QuizDao {

  @Insert
  suspend fun insert(quiz: QuizEntity): Long

  @Query("SELECT * FROM tb_quiz")
  suspend fun getAll(): List<QuizEntity>
}
