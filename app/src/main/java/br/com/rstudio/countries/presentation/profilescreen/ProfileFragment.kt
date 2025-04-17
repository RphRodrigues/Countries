package br.com.rstudio.countries.presentation.profilescreen

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.goToBottomNavigationHome
import br.com.rstudio.countries.arch.extension.setupBackPressedCallback
import br.com.rstudio.countries.presentation.profilescreen.view.ProfileRankingView

private const val CURRENT_PROGRESS = 74
private const val MAX_PROGRESS = 100

class ProfileFragment : Fragment(R.layout.fragment_profile) {

  private lateinit var progressView: ProgressBar
  private lateinit var starView: ProfileRankingView
  private lateinit var globalView: ProfileRankingView
  private lateinit var localView: ProfileRankingView

  private val callback = {
    activity.goToBottomNavigationHome()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupBackPressedCallback(callback)
    setupView(view)
    bindView()
  }

  private fun setupView(view: View) {
    progressView = view.findViewById(R.id.profile_progress)
    starView = view.findViewById(R.id.profile_ranking_1)
    globalView = view.findViewById(R.id.profile_ranking_2)
    localView = view.findViewById(R.id.profile_ranking_3)
  }

  override fun onResume() {
    super.onResume()
    progressView.progress = CURRENT_PROGRESS
    progressView.max = MAX_PROGRESS
  }

  private fun bindView() {
    starView.bindIcon(R.drawable.star_icon)
    starView.bindTitle("Pontos")
    starView.bindValue("590")

    globalView.bindIcon(R.drawable.global_icon)
    globalView.bindTitle("Ranking Global")
    globalView.bindValue("#1,438")

    localView.bindIcon(R.drawable.local_icon)
    localView.bindTitle("Ranking Brasil")
    localView.bindValue("#56")
  }

  companion object {
    fun newInstance() = ProfileFragment()
  }
}
