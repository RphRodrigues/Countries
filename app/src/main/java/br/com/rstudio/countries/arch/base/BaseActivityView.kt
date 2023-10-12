package br.com.rstudio.countries.arch.base

import br.com.rstudio.countries.arch.model.ErrorModel

interface BaseActivityView {
  fun showError(err: ErrorModel, errAction: () -> Unit)
  fun hideError()
  fun showLoader()
  fun hideLoader()
}