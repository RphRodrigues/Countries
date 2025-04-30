package br.com.rstudio.countries.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.children
import androidx.core.view.isVisible
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.base.BaseActivityView
import br.com.rstudio.countries.arch.extension.ScreenName
import br.com.rstudio.countries.arch.extension.replaceFragment
import br.com.rstudio.countries.arch.featuretoggle.FeatureToggles
import br.com.rstudio.countries.arch.featuretoggle.RemoteConfig
import br.com.rstudio.countries.arch.model.ErrorModel
import br.com.rstudio.countries.arch.notification.NotificationPermissionUtil
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.BUTTON
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.CLICK
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.ERROR_CODE
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.ERROR_MESSAGE
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.HIRE_ERROR
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.HIRE_LOADER
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.SHOW_ERROR
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.SHOW_LOADER
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.arch.prefs.AppPrefs
import br.com.rstudio.countries.arch.util.Constants
import br.com.rstudio.countries.arch.widget.FeedbackView
import br.com.rstudio.countries.arch.widget.ProgressView
import br.com.rstudio.countries.notification.NotificationJustificationBottomSheet
import br.com.rstudio.countries.presentation.favoritescreen.FavoriteFragment
import br.com.rstudio.countries.presentation.homescreen.v1.ListFragment
import br.com.rstudio.countries.presentation.homescreen.v2.HomeFragment
import br.com.rstudio.countries.presentation.overviewscreen.v2.CountryOverviewFragment
import br.com.rstudio.countries.presentation.profilescreen.ProfileFragment
import br.com.rstudio.countries.presentation.quizscreen.QuizFragment
import br.com.rstudio.countries.presentation.rankingscreen.RankingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import org.koin.android.ext.android.inject
import timber.log.Timber

@Suppress("TooManyFunctions")
class MainActivity : AppCompatActivity(), BaseActivityView {

  private var feedbackView: FeedbackView? = null
  private var progressView: ProgressView? = null
  private var bottomNavigationView: BottomNavigationView? = null

  private val analytics: AnalyticsReport by inject()
  private val remoteConfig: RemoteConfig by inject()
  private val appPrefs: AppPrefs by inject()
  private val notificationUtil: NotificationPermissionUtil by inject()

  private val requestPermissionLauncher =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
      if (isGranted) {
        Timber.tag(Constants.PERMISSION).d("Notification permission granted")

        analytics.trackEvent(CLICK, mapOf(BUTTON to "notification_permission_granted"))
        appPrefs.cleanLastAskedTimeNotificationPermission()
      } else {
        Timber.tag(Constants.PERMISSION).d("Notification permission denied")
        analytics.trackEvent(CLICK, mapOf(BUTTON to "notification_permission_denied"))
      }
    }

  private val onItemSelectedListener = object : NavigationBarView.OnItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
      return when (item.itemId) {
        R.id.action_home -> {
          redirectToHomeScreen()
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
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupView()

    bottomNavigationView?.setOnItemSelectedListener(onItemSelectedListener)
    handleIntent(intent)
    setupBottomNavigationToggles()
    askNotificationPermission()

    // When the back stack is empty and the back button is clicked,
    // select the Home fragment
    supportFragmentManager.addOnBackStackChangedListener {
      if (supportFragmentManager.backStackEntryCount == 0) {
        bottomNavigationView?.selectedItemId = R.id.action_home
      }
    }
  }

  private fun setupView() {
    feedbackView = findViewById(R.id.feedback_view)
    progressView = findViewById(R.id.progress_view)
    bottomNavigationView = findViewById(R.id.bottom_navigation)
  }

  private fun redirectToHomeScreen() {
    if (remoteConfig.getBoolean(FeatureToggles.SHOW_HOME_SCREEN_V2)) {
      replaceFragment(HomeFragment.newInstance())
    } else {
      replaceFragment(ListFragment.newInstance())
    }
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

  @SuppressLint("InlinedApi")
  private fun shouldAskedNotificationPermission() =
    ContextCompat.checkSelfPermission(
      this,
      Manifest.permission.POST_NOTIFICATIONS
    ) != PackageManager.PERMISSION_GRANTED && notificationUtil.shouldAskNotificationPermissionAgain()

  private fun askNotificationPermission() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    if (!shouldAskedNotificationPermission()) return

    when {
      shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
        Timber.tag(Constants.PERMISSION).d("User denied once — show a custom dialog explaining why you need it")
        showNotificationJustification(redirectToSettings = true)
      }

      ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.POST_NOTIFICATIONS
      ) != PackageManager.PERMISSION_GRANTED -> {
        Timber.tag(Constants.PERMISSION).d("First time asking, or user chose \"Don't ask again\"")
        showNotificationJustification()
      }
    }
  }

  @SuppressLint("InlinedApi")
  private fun showNotificationJustification(redirectToSettings: Boolean = false) {
    with(NotificationJustificationBottomSheet.create()) {
      onAllowClick = {
        if (redirectToSettings) {
          analytics.trackEvent(CLICK, mapOf(BUTTON to "open_app_settings"))

          startActivity(
            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
              putExtra(Settings.EXTRA_APP_PACKAGE, context?.packageName)
            }
          )
        } else {
          analytics.trackEvent(CLICK, mapOf(BUTTON to "allow_notification"))
          requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
      }
      onDismissClick = {
        analytics.trackEvent(CLICK, mapOf(BUTTON to "maybe_later"))
        Timber.tag(Constants.PERMISSION).d("User declined notification permission")
      }
      onViewCreate = {
        if (redirectToSettings) updateBottomSheet()
      }
      show(supportFragmentManager, ScreenName)
    }
    appPrefs.setLastAskedTimeNotificationPermission()
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
      ERROR_CODE to err.code.toString(),
      ERROR_MESSAGE to getString(err.message)
    ).also { params ->
      analytics.trackEvent(SHOW_ERROR, params)
    }

    feedbackView?.run {
      setTitle(getString(R.string.error))
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
    if (intent?.data != null) {
      handleDeepLink(intent)
    } else {
      bottomNavigationView?.selectedItemId = R.id.action_home
    }
  }

  // TODO(Refactor using the chain of responsibility pattern to resolve deep links)
  private fun handleDeepLink(intent: Intent) {
    val path = intent.data?.getQueryParameter("path")
    val country = intent.data?.getQueryParameter("country")

    // if path null or empty redirect user to home and ignore other params
    if (path.isNullOrEmpty()) {
      Timber.tag(Constants.DEEP_LINK).w("Deep link path is null or empty")
      bottomNavigationView?.selectedItemId = R.id.action_home
      return
    }

    bottomNavigationView?.selectedItemId = when (path) {
      getString(R.string.deep_link_screen_ranking) -> R.id.action_ranking
      getString(R.string.deep_link_screen_quiz) -> R.id.action_quiz
      getString(R.string.deep_link_screen_favorite) -> R.id.action_favorite
      getString(R.string.deep_link_screen_profile) -> R.id.action_profile
      "home/overview" -> {
        if (country.isNullOrEmpty()) {
          Timber.tag(Constants.DEEP_LINK).w("Deep link country is null or empty")
          redirectToHomeScreen()
          return
        }

        replaceFragment(CountryOverviewFragment.newInstance(country))

        // disable listener to avoid redirect to home fragment
        bottomNavigationView?.setOnItemSelectedListener(null)
        R.id.action_home
      }

      else -> {
        R.id.action_home
      }
    }

    // enable listener again
    bottomNavigationView?.setOnItemSelectedListener(onItemSelectedListener)

    // clean intent
    intent.data == null
  }

  companion object {
    fun createIntent(context: Context, data: String? = null): Intent {
      return Intent(context, MainActivity::class.java).apply {
        this.data = data?.toUri()
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      }
    }
  }
}
