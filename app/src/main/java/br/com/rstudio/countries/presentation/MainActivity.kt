package br.com.rstudio.countries.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.base.BaseActivityView
import br.com.rstudio.countries.arch.extension.replaceFragment
import br.com.rstudio.countries.arch.featuretoggle.FeatureToggles
import br.com.rstudio.countries.arch.featuretoggle.RemoteConfig
import br.com.rstudio.countries.arch.model.ErrorModel
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.BUTTON
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.CLICK
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.HIRE_ERROR
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.HIRE_LOADER
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.SHOW_ERROR
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.SHOW_LOADER
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.arch.util.Constants
import br.com.rstudio.countries.arch.widget.FeedbackView
import br.com.rstudio.countries.arch.widget.ProgressView
import br.com.rstudio.countries.presentation.favoritescreen.FavoriteFragment
import br.com.rstudio.countries.presentation.homescreen.v1.ListFragment
import br.com.rstudio.countries.presentation.homescreen.v2.HomeFragment
import br.com.rstudio.countries.presentation.profilescreen.ProfileFragment
import br.com.rstudio.countries.presentation.quizscreen.QuizFragment
import br.com.rstudio.countries.presentation.rankingscreen.RankingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity(), BaseActivityView {

  private var feedbackView: FeedbackView? = null
  private var progressView: ProgressView? = null
  private var bottomNavigationView: BottomNavigationView? = null

  private val analytics: AnalyticsReport by inject()
  private val remoteConfig: RemoteConfig by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupView()
    setupBottomNavigation()
    setupBottomNavigationToggles()
    handleIntent(intent)
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
          if (remoteConfig.getBoolean(FeatureToggles.SHOW_HOME_SCREEN_V2)) {
            replaceFragment(HomeFragment.newInstance())
          } else {
            replaceFragment(ListFragment.newInstance())
          }
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

  private fun setupBottomNavigationToggles() {
    bottomNavigationView?.isVisible = remoteConfig.getBoolean(FeatureToggles.SHOW_BOTTOM_NAVIGATION)

    bottomNavigationView?.menu?.children?.forEach { menuItem: MenuItem ->
      menuItem.isVisible = when (menuItem.title) {
        getString(R.string.home) -> remoteConfig.getBoolean(FeatureToggles.SHOW_BOTTOM_NAV_HOME)
        getString(R.string.ranking) -> remoteConfig.getBoolean(FeatureToggles.SHOW_BOTTOM_NAV_RANKING)
        getString(R.string.quiz) -> remoteConfig.getBoolean(FeatureToggles.SHOW_BOTTOM_NAV_QUIZ)
        getString(R.string.favorites) -> remoteConfig.getBoolean(FeatureToggles.SHOW_BOTTOM_NAV_FAVORITE)
        getString(R.string.profile) -> remoteConfig.getBoolean(FeatureToggles.SHOW_BOTTOM_NAV_PROFILE)
        else -> false
      }
    }
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
      setupButton(getString(R.string.try_again)) {
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

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    setIntent(intent)
    handleIntent(intent)
  }

  private fun handleIntent(intent: Intent?) {
    Timber.tag(Constants.DEEP_LINK).d("action ${intent?.action}")
    Timber.tag(Constants.DEEP_LINK).d("data ${intent?.data}")

    val screen = intent?.data?.getQueryParameter(getString(R.string.deep_link_screen))

    bottomNavigationView?.selectedItemId = when (screen) {
      getString(R.string.deep_link_screen_ranking) -> R.id.action_ranking
      getString(R.string.deep_link_screen_quiz) -> R.id.action_quiz
      getString(R.string.deep_link_screen_favorite) -> R.id.action_favorite
      getString(R.string.deep_link_screen_profile) -> R.id.action_profile
      else -> R.id.action_home
    }
  }

  companion object {
    fun createIntent(context: Context): Intent {
      return Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      }
    }
  }
}
