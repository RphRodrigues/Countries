package br.com.rstudio.countries.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "tb_quiz")
data class QuizEntity(
  @PrimaryKey val id: UUID = UUID.randomUUID(),
  @ColumnInfo(name = "flag") val flag: String,
  @ColumnInfo(name = "option1") val option1: String,
  @ColumnInfo(name = "option2") val option2: String,
  @ColumnInfo(name = "option3") val option3: String,
  @ColumnInfo(name = "option4") val option4: String,
  @ColumnInfo(name = "question") val question: String,
  @ColumnInfo(name = "correct_answer") val correctAnswer: String,
  @ColumnInfo(name = "selected_option") val selectedOption: String,
  @ColumnInfo(name = "is_answer_correct") val isAnswerCorrect: Boolean,
  @ColumnInfo(name = "date") val date: Date = Date()
)
