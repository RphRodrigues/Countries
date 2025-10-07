package br.com.rstudio.countries.notification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.ScreenName
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.BUTTON
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsEvent.CLICK
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.arch.util.Constants
import br.com.rstudio.countries.databinding.BottomSheetNotificationJustificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import timber.log.Timber

class NotificationJustificationBottomSheet : BottomSheetDialogFragment() {

  lateinit var onAllowPermissionListener: () -> Unit
  private var binding: BottomSheetNotificationJustificationBinding? = null
  private val analytics: AnalyticsReport by inject()

  private val isRedirectSettings: Boolean
    get() = arguments?.getBoolean(REDIRECT_SETTINGS) == true

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = BottomSheetNotificationJustificationBinding.inflate(inflater, container, false)
    return binding?.root
  }

  @SuppressLint("InlinedApi")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    if (isRedirectSettings) {
      binding?.allowButton?.text = getString(R.string.open_settings)
      binding?.maybeLaterTextView?.isVisible = false
    }

    binding?.allowButton?.setOnClickListener {
      if (isRedirectSettings) {
        analytics.trackEvent(CLICK, mapOf(BUTTON to "open_app_settings"))

        startActivity(
          Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context?.packageName)
          }
        )
      } else {
        analytics.trackEvent(CLICK, mapOf(BUTTON to "allow_notification"))
        onAllowPermissionListener.invoke()
      }
      dismiss()
    }

    binding?.maybeLaterTextView?.setOnClickListener {
      analytics.trackEvent(CLICK, mapOf(BUTTON to "maybe_later"))
      Timber.tag(Constants.PERMISSION).d("User declined notification permission")
      dismiss()
    }

    analytics.trackScreenView(ScreenName)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    binding = null
  }

  companion object {
    private const val REDIRECT_SETTINGS = "redirect_settings"

    fun create(isRedirectSettings: Boolean) = NotificationJustificationBottomSheet().apply {
      arguments = bundleOf(REDIRECT_SETTINGS to isRedirectSettings)
    }
  }
}
