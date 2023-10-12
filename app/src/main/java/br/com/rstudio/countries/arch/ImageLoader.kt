package br.com.rstudio.countries.arch

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

interface ImageLoader {
  fun load(url: String, targetView: ImageView, onCompleted: (() -> Unit)? = null)
}

class GlideImageLoader(private val glide: RequestManager) : ImageLoader {
  override fun load(url: String, targetView: ImageView, onCompleted: (() -> Unit)?) {
    glide
      .load(url)
      .listener(loadImageListener(onCompleted))
      .into(targetView)
  }
}

private fun loadImageListener(onCompleted: (() -> Unit)?) = object : RequestListener<Drawable> {
  override fun onLoadFailed(
    e: GlideException?,
    model: Any?,
    target: Target<Drawable>?,
    isFirstResource: Boolean
  ): Boolean {
    onCompleted?.invoke()
    return true
  }

  override fun onResourceReady(
    resource: Drawable?,
    model: Any?,
    target: Target<Drawable>?,
    dataSource: DataSource?,
    isFirstResource: Boolean
  ): Boolean {
    onCompleted?.invoke()
    return false
  }
}