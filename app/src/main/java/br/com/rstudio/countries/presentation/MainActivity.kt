package br.com.rstudio.countries.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.base.BaseActivityView
import br.com.rstudio.countries.arch.extension.replaceFragment
import br.com.rstudio.countries.arch.model.ErrorModel
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.BUTTON
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.CLICK
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.HIRE_ERROR
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.HIRE_LOADER
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.SHOW_ERROR
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.SHOW_LOADER
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.arch.widget.FeedbackView
import br.com.rstudio.countries.arch.widget.ProgressView
import br.com.rstudio.countries.presentation.favoritescreen.FavoriteFragment
import br.com.rstudio.countries.presentation.listscreen.ListFragment
import br.com.rstudio.countries.presentation.profilescreen.ProfileFragment
import br.com.rstudio.countries.presentation.quizscreen.QuizFragment
import br.com.rstudio.countries.presentation.rankingscreen.RankingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), BaseActivityView {

  private var feedbackView: FeedbackView? = null
  private var progressView: ProgressView? = null
  private var bottomNavigationView: BottomNavigationView? = null

  private val analytics: AnalyticsReport by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupView()
    setupBottomNavigation()
  }

  private fun setupView() {
    feedbackView = findViewById(R.id.feedback_view)
    progressView = findViewById(R.id.progress_view)
    bottomNavigationView = findViewById(R.id.bottom_navigation)
  }

  private fun setupBottomNavigation() {
    bottomNavigationView?.setOnItemSelectedListener {
      when (it.itemId) {
        R.id.action_home -> {
          replaceFragment(ListFragment.newInstance())
          true
        }

        R.id.action_ranking -> {
          replaceFragment(RankingFragment.newInstance())
          true
        }

        R.id.action_quiz -> {
          replaceFragment(QuizFragment.newInstance())
          true
        }

        R.id.action_favorite -> {
          replaceFragment(FavoriteFragment.newInstance())
          true
        }

        R.id.action_profile -> {
          replaceFragment(ProfileFragment.newInstance())
          true
        }

        else -> false
      }
    }

    bottomNavigationView?.selectedItemId = R.id.action_home
  }

  override fun showLoader() {
    analytics.trackEvent(SHOW_LOADER)
    progressView?.isVisible = true
  }

  override fun hideLoader() {
    analytics.trackEvent(HIRE_LOADER)
    progressView?.isVisible = false
  }

  override fun showError(err: ErrorModel, errAction: () -> Unit) {

    mapOf(
      "error_code" to err.code.toString(),
      "error_message" to applicationContext?.getText(err.message).toString()
    ).also { params ->
      analytics.trackEvent(SHOW_ERROR, params)
    }

    feedbackView?.run {
      setTitle("Error!")
      setMessage(err.message)
      setupButton("Tentar novamente") {
        analytics.trackEvent(CLICK, mapOf(BUTTON to "try_again"))
        errAction()
      }
      isVisible = true
    }
  }

  override fun hideError() {
    analytics.trackEvent(HIRE_ERROR)
    feedbackView?.isVisible = false
  }

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      }
    }
  }
}
