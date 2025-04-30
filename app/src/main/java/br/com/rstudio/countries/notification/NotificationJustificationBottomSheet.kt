package br.com.rstudio.countries.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.ScreenName
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.databinding.BottomSheetNotificationJustificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.getValue
import org.koin.android.ext.android.inject

class NotificationJustificationBottomSheet : BottomSheetDialogFragment() {

  lateinit var onAllowClick: () -> Unit
  lateinit var onDismissClick: () -> Unit
  lateinit var onViewCreate: () -> Unit
  private var binding: BottomSheetNotificationJustificationBinding? = null
  private val analytics: AnalyticsReport by inject()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = BottomSheetNotificationJustificationBinding.inflate(inflater, container, false)

    onViewCreate()

    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding?.allowButton?.setOnClickListener {
      onAllowClick()
      dismiss()
    }

    binding?.maybeLaterTextView?.setOnClickListener {
      onDismissClick()
      dismiss()
    }

    analytics.trackScreenView(ScreenName)
  }

  fun updateBottomSheet() {
    binding?.allowButton?.text = getString(R.string.open_settings)
    binding?.maybeLaterTextView?.isVisible = false
  }

  override fun onDestroyView() {
    super.onDestroyView()
    binding = null
  }

  companion object {
    fun create() = NotificationJustificationBottomSheet()
  }
}
