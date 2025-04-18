package br.com.rstudio.countries.arch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.rstudio.countries.arch.database.converters.DateConverter
import br.com.rstudio.countries.arch.database.converters.UUIDConverter
import br.com.rstudio.countries.data.dao.QuizDao
import br.com.rstudio.countries.data.entity.QuizEntity

@Database(
  version = 1,
  exportSchema = false,
  entities = [
    QuizEntity::class
  ]
)
@TypeConverters(
  UUIDConverter::class,
  DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun quizDao(): QuizDao
}
