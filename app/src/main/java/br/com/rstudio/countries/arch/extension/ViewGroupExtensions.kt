package br.com.rstudio.countries.arch.extension

import android.view.ViewGroup

/**
 * Updates the [MarginLayoutParams] of a [ViewGroup] using a DSL-style block.
 *
 * This extension function simplifies modifying margin layout parameters
 * without needing to cast or manually reassign them.
 *
 * Example usage:
 * ```
 * myViewGroup.updateMarginLayoutParams {
 *     marginStart = 16.dpToPx(context)
 *     topMargin = 8.dpToPx(context)
 * }
 * ```
 *
 * @param block Lambda block to update the [ViewGroup.MarginLayoutParams]
 * @throws ClassCastException if [layoutParams] is not an instance of [ViewGroup.MarginLayoutParams]
 */
fun ViewGroup.updateMarginLayoutParams(block: ViewGroup.MarginLayoutParams.() -> Unit) {
  val params = this.layoutParams as ViewGroup.MarginLayoutParams
  block(params)
  this.layoutParams = params
}
