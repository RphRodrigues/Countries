package br.com.rstudio.countries.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.base.BaseActivityView
import br.com.rstudio.countries.arch.model.ErrorModel
import br.com.rstudio.countries.arch.widget.FeedbackView
import br.com.rstudio.countries.arch.widget.ProgressView
import br.com.rstudio.countries.arch.extension.replaceFragment
import br.com.rstudio.countries.presentation.listscreen.ListFragment

class MainActivity : AppCompatActivity(), BaseActivityView {

  private var feedbackView: FeedbackView? = null
  private var progressView: ProgressView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupView()
    replaceFragment(ListFragment.newInstance())
  }

  private fun setupView() {
    feedbackView = findViewById(R.id.feedback_view)
    progressView = findViewById(R.id.progress_view)
  }

  override fun showLoader() {
    progressView?.isVisible = true
  }

  override fun hideLoader() {
    progressView?.isVisible = false
  }

  override fun showError(err: ErrorModel, errAction: () -> Unit) {
    feedbackView?.run {
      setTitle("Error!")
      setMessage(err.message)
      setupButton("Tentar novamente", errAction)
      isVisible = true
    }
  }

  override fun hideError() {
    feedbackView?.isVisible = false
  }
}