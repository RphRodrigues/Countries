package br.com.rstudio.countries.arch

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

interface ImageLoader {
  fun load(url: String, targetView: ImageView, onCompleted: ((drawable: Drawable?) -> Unit)? = null)
}

class GlideImageLoader(private val glide: RequestManager) : ImageLoader {
  override fun load(url: String, targetView: ImageView, onCompleted: ((drawable: Drawable?) -> Unit)?) {
    glide
      .load(url)
      .listener(imageLoaderListener(onCompleted))
      .into(targetView)
  }
}

private fun imageLoaderListener(onCompleted: ((drawable: Drawable?) -> Unit)?) =
  object : RequestListener<Drawable> {

    override fun onLoadFailed(
      e: GlideException?,
      model: Any?,
      target: Target<Drawable>?,
      isFirstResource: Boolean
    ): Boolean {
      onCompleted?.invoke(null)
      return true
    }

    override fun onResourceReady(
      resource: Drawable?,
      model: Any?,
      target: Target<Drawable>?,
      dataSource: DataSource?,
      isFirstResource: Boolean
    ): Boolean {
      onCompleted?.invoke(resource)
      return false
    }
  }
