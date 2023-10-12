package br.com.rstudio.countries.arch.network

import br.com.rstudio.countries.arch.model.ErrorModel

interface NetworkView {
  fun showError(err: ErrorModel)
  fun hideError()
  fun showLoader()
  fun hideLoader()
}