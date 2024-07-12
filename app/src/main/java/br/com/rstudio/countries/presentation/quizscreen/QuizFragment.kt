package br.com.rstudio.countries.presentation.quizscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.getBottomLeftColor
import br.com.rstudio.countries.arch.extension.getBottomRightColor
import br.com.rstudio.countries.arch.extension.goToBottomNavigationHome
import br.com.rstudio.countries.arch.extension.setupBackPressedCallback
import br.com.rstudio.countries.databinding.FragmentQuizBinding

class QuizFragment : Fragment(R.layout.fragment_quiz) {

  private lateinit var binding: FragmentQuizBinding

  private val callback = {
    activity.goToBottomNavigationHome()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentQuizBinding.bind(view)
    setupBackPressedCallback(callback)
    loadImage("https://flagcdn.com/w320/pm.png")
  }

  private fun loadImage(url: String) = binding.apply {
    quizFlagImageView.load(url) { drawable ->
      quizFlagDetailsViewLeft.setBackgroundColor(drawable.getBottomLeftColor())
      quizFlagDetailsViewRight.setBackgroundColor(drawable.getBottomRightColor())
    }
  }

  companion object {
    fun newInstance() = QuizFragment()
  }
}
