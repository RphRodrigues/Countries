package br.com.rstudio.countries.presentation.quizscreen

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.getBottomLeftColor
import br.com.rstudio.countries.arch.extension.getBottomRightColor
import br.com.rstudio.countries.arch.extension.goToBottomNavigationHome
import br.com.rstudio.countries.arch.extension.setSafeOnClickListener
import br.com.rstudio.countries.arch.extension.setupBackPressedCallback
import br.com.rstudio.countries.data.model.Quiz
import br.com.rstudio.countries.databinding.FragmentQuizBinding
import br.com.rstudio.countries.presentation.quizscreen.view.QuizFeedbackView.FeedbackType
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizFragment : Fragment(R.layout.fragment_quiz) {

  private lateinit var binding: FragmentQuizBinding
  private val viewModel by viewModel<QuizViewModel>()

  private val callback = {
    if (binding.quizFeedback.isGone) {
      activity.goToBottomNavigationHome()
    }
    binding.quizFeedback.visibility = View.GONE
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentQuizBinding.bind(view)
    setupBackPressedCallback(callback)
    setupListeners()
    setupObservers()
  }

  private fun setupListeners() = binding.apply {
    quizButton.setSafeOnClickListener {
      val buttonId = quizRadioGroup.checkedRadioButtonId

      if (buttonId == BUTTON_NOT_FOUND) {
        Toast.makeText(context, getString(R.string.select_a_option), Toast.LENGTH_SHORT).show()
        return@setSafeOnClickListener
      }

      val answer = view?.findViewById<RadioButton>(buttonId)?.text.toString()
      viewModel.verifyAnswer(answer)
    }
  }

  override fun onStart() {
    super.onStart()
    viewModel.getQuiz()
  }

  private fun setupObservers() {
    viewModel.quiz
      .observe(viewLifecycleOwner) {
        setupQuiz(it)
        uncheckRadioButton()
      }

    viewModel.answerState
      .observe(viewLifecycleOwner) {
        showQuizFeedback(it)
        viewModel.getQuiz()
      }
  }

  private fun uncheckRadioButton() {
    binding.quizRadioGroup.clearCheck()
  }

  @Suppress("MagicNumber")
  private fun setupQuiz(quiz: Quiz) {
    loadImage(quiz.flag)

    binding.quizQuestionTextView.text = quiz.question

    binding.quizRadioButton1.text = quiz.answerOptions[0]
    binding.quizRadioButton2.text = quiz.answerOptions[1]
    binding.quizRadioButton3.text = quiz.answerOptions[2]
    binding.quizRadioButton4.text = quiz.answerOptions[3]
  }

  private fun loadImage(url: String) = binding.apply {
    quizFlagImageView.load(url) { drawable ->
      quizFlagDetailsViewLeft.setBackgroundColor(drawable.getBottomLeftColor())
      quizFlagDetailsViewRight.setBackgroundColor(drawable.getBottomRightColor())
    }
  }

  private fun showQuizFeedback(answerState: AnswerState) = binding.apply {
    when (answerState) {
      AnswerState.Correct -> quizFeedback.apply {
        setup(FeedbackType.Congratulation, viewModel.quiz.value?.answer)
      }

      AnswerState.Wrong -> quizFeedback.apply {
        setup(FeedbackType.BetterLuckNextTime, viewModel.quiz.value?.answer)
      }

      else -> Unit
    }
  }

  companion object {
    private const val BUTTON_NOT_FOUND = -1
    fun newInstance() = QuizFragment()
  }
}
