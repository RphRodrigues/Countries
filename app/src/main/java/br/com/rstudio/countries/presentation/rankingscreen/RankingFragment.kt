package br.com.rstudio.countries.presentation.rankingscreen

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.goToBottomNavigationHome
import br.com.rstudio.countries.arch.extension.setupBackPressedCallback
import br.com.rstudio.countries.presentation.rankingscreen.adapter.RankingAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior

class RankingFragment : Fragment(R.layout.fragment_ranking) {

  private lateinit var podiumImageView: ImageView
  private lateinit var bottomSheetView: FrameLayout
  private lateinit var recyclerView: RecyclerView
  private lateinit var containerView: ConstraintLayout

  private val callback = {
    activity.goToBottomNavigationHome()
  }

  private val windowHeight: Int
    get() {
      val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
      val displayMetrics = DisplayMetrics()

      windowManager.defaultDisplay.getMetrics(displayMetrics)

      return displayMetrics.heightPixels
    }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupBackPressedCallback(callback)
    setupViews(view)
    bindViews()
  }

  private fun setupViews(view: View) {
    podiumImageView = view.findViewById(R.id.ranking_podium_image)
    bottomSheetView = view.findViewById(R.id.ranking_bottom_sheet)
    recyclerView = view.findViewById(R.id.ranking_list_recycler_view)
    containerView = view.findViewById(R.id.ranking_header_container)

    containerView
      .viewTreeObserver
      .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
          containerView.viewTreeObserver.removeOnGlobalLayoutListener(this)

          bottomSheetView.updateLayoutParams {
            val bottomNavigationHeight = activity?.findViewById<View>(R.id.bottom_navigation)?.height ?: 0
            height = windowHeight - (containerView.height + bottomNavigationHeight)
          }
        }
      })

    BottomSheetBehavior.from(bottomSheetView).apply {
      peekHeight = (windowHeight * EIGHT_PERCENT).toInt()
      state = BottomSheetBehavior.STATE_COLLAPSED
    }
  }

  private fun bindViews() {
    val items = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
    recyclerView.adapter = RankingAdapter(items)
    recyclerView.setHasFixedSize(true)
  }

  companion object {
    private const val EIGHT_PERCENT = 0.08

    fun newInstance() = RankingFragment()
  }
}
