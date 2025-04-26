package br.com.rstudio.countries.arch.widget

import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

private const val ALPHA = 0.7f
private const val DURATION = 1000L

fun imageLoaderShimmerDrawable() = ShimmerDrawable().apply {

  val shimmer = Shimmer.AlphaHighlightBuilder() // The attributes for a ShimmerDrawable is set by this builder
    .setDuration(DURATION) // how long the shimmering animation takes to do one full sweep
    .setBaseAlpha(ALPHA) // the alpha of the underlying children
    .setHighlightAlpha(ALPHA) // the shimmer alpha amount
    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
    .setAutoStart(true)
    .build()

  setShimmer(shimmer)
}
