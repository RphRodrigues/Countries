package br.com.rstudio.countries.presentation.quizscreen.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.extension.setSafeOnClickListener
import br.com.rstudio.countries.databinding.QuizFeedbackViewBinding.inflate
import br.com.rstudio.countries.presentation.quizscreen.view.QuizFeedbackView.FeedbackType.BetterLuckNextTime
import br.com.rstudio.countries.presentation.quizscreen.view.QuizFeedbackView.FeedbackType.Congratulation

class QuizFeedbackView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding = inflate(LayoutInflater.from(context), this, true)

  fun setup(type: FeedbackType, answer: String?) {
    when (type) {
      is Congratulation -> {
        setIcon(R.drawable.ic_feeckback_correct)
        setTitle(context.getString(R.string.congratulations))
        setMessage(context.getString(R.string.you_got_it_right, answer))
      }

      is BetterLuckNextTime -> {
        setIcon(R.drawable.ic_feeckback_wrong)
        setTitle(context.getString(R.string.better_luck_next_time))
        setMessage(context.getString(R.string.the_correct_answer_is, answer))
      }
    }

    visibility = View.VISIBLE

    setupAppBar()
  }

  private fun setIcon(@DrawableRes icon: Int) = binding.apply {
    quizFeedbackIcon.setImageDrawable(AppCompatResources.getDrawable(context, icon))
  }

  private fun setTitle(text: String) = binding.apply {
    quizFeedbackTitleTextView.text = text
  }

  private fun setMessage(text: String) = binding.apply {
    quizFeedbackMessageTextView.text = text
  }

  private fun setupAppBar() = binding.apply {
    quizFeedbackAppbar.iconView.setSafeOnClickListener {
      visibility = View.GONE
    }
  }

  sealed class FeedbackType {
    object Congratulation : FeedbackType()
    object BetterLuckNextTime : FeedbackType()
  }
}
