package br.com.rstudio.countries.presentation.quizscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rstudio.countries.arch.util.Constants
import br.com.rstudio.countries.data.model.Quiz
import br.com.rstudio.countries.domain.GenerateQuizUseCase
import br.com.rstudio.countries.domain.SaveQuizAnsweredUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class QuizViewModel(
  private val generateQuizUseCase: GenerateQuizUseCase,
  private val saveQuizAnsweredUseCase: SaveQuizAnsweredUseCase
) : ViewModel() {

  private val _quiz = MutableLiveData<Quiz>()
  val quiz: LiveData<Quiz> = _quiz

  private val _answerState = MutableLiveData<AnswerState>(AnswerState.Initial)
  val answerState: LiveData<AnswerState> = _answerState

  fun getQuiz() {
    viewModelScope.launch {
      val result = generateQuizUseCase()
      _quiz.value = result
    }
  }

  fun verifyAnswer(selectedOption: String) {
    _answerState.value = when {
      _quiz.value?.answer == selectedOption -> AnswerState.Correct
      else -> AnswerState.Wrong
    }

    salve(_answerState.value == AnswerState.Correct, selectedOption)
  }

  @Suppress("TooGenericExceptionCaught")
  private fun salve(isCorrect: Boolean, selectedAnswer: String) = _quiz.value?.let { quiz ->
    viewModelScope.launch {

      try {
        val id = saveQuizAnsweredUseCase(quiz, isCorrect, selectedAnswer)
        Timber.tag(Constants.ROOM).d("Saved -> uuid $id")
      } catch (e: Exception) {
        Timber.tag(Constants.ROOM).w("Error: $e")
      }
    }
  }
}
