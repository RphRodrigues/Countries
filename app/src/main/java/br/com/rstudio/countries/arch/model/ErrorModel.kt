package br.com.rstudio.countries.arch.model

import androidx.annotation.StringRes

class ErrorModel(
  val type: ErrorType,
  val code: Int,
  @StringRes
  val message: Int
)

sealed class ErrorType {
  object Api : ErrorType()
  object Internal : ErrorType()
}
