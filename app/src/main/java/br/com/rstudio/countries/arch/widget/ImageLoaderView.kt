package br.com.rstudio.countries.arch.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import br.com.rstudio.countries.arch.ImageLoader
import br.com.rstudio.countries.databinding.ImageLoaderViewBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ImageLoaderView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), KoinComponent {

  private val imageLoader by inject<ImageLoader>()

  private var binding: ImageLoaderViewBinding =
    ImageLoaderViewBinding.inflate(LayoutInflater.from(context), this, true)

  fun load(url: String, callback: ((drawable: Drawable?) -> Unit)? = null) {
    imageLoader.load(url, binding.loadImageView) { drawable ->
      callback?.invoke(drawable)
    }
  }
}
