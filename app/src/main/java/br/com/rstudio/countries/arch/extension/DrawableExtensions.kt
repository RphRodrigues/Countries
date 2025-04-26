package br.com.rstudio.countries.arch.extension

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap

fun Drawable?.getBottomLeftColor(): Int {
  val bitmap = this?.toBitmap()
  return bitmap?.getPixel(0, bitmap.height - 1) ?: Color.TRANSPARENT
}

fun Drawable?.getBottomRightColor(): Int {
  val bitmap = this?.toBitmap()
  return bitmap?.getPixel(bitmap.width - 1, bitmap.height - 1) ?: Color.TRANSPARENT
}
