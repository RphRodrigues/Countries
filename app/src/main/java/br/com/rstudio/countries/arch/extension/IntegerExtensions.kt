package br.com.rstudio.countries.arch.extension

import android.content.Context

/**
 * Converts a value in density-independent pixels (dp) to pixels (px),
 * based on the screen density of the provided [context].
 *
 * This is useful for ensuring consistent sizing across different screen densities.
 *
 * Example:
 * ```
 * val paddingPx = 16.dpToPx(context)
 * ```
 *
 * @receiver The dp value to convert.
 * @param context The context used to access display metrics.
 * @return The equivalent pixel value as a [Float].
 */
fun Int.dpToPx(context: Context): Float {
  return this * context.resources.displayMetrics.density
}
