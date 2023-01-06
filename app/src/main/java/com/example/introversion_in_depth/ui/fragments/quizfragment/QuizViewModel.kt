package com.example.introversion_in_depth.ui.fragments.quizfragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateHandler
import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.data.entities.Quiz
import com.example.introversion_in_depth.data.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizViewModel(
    private val view: ViewStateHandler,
    private val quizRepository: QuizRepository
    ): BaseViewModel<QuizState, QuizAction>() {

    init {
        collect()
    }

    private lateinit var questions: List<String>
    private var quizId = 0
    private val answersId = ArrayList<Int>()

    /**
     * When the fragment opens first time,save new Quiz object to db. Also, save new answer to db and to a list in viewModel.
     * Update view state with the new question.
     *
     *
     * **/


    override fun collect() {
        state.onEach {
            when(it) {
                is QuizState.NextQuestion -> {

                }
            }
            view.handleState(it)
        }.launchIn(viewModelScope)
    }

    override fun process(action: QuizAction) {
        CoroutineScope(Dispatchers.Default).launch {
            when(action) {
                QuizAction.GetQuiz -> {

                }

                QuizAction.ToNextQuestion -> {

                }

                QuizAction.ToPreviousQuestion -> {
                }

                is QuizAction.StartNewQuiz -> {
                    questions = action.questions

                    quizId = withContext(Dispatchers.Default)
                    { quizRepository.insertQuiz(Quiz()).toInt() }

                    val answerId = withContext(Dispatchers.Default)
                    { quizRepository.insertAnswer(Answer(quizId = quizId)).toInt() }

                    answersId.add(answerId)
                }

                else -> {}
            }
        }
    }

    override fun clear() {
    }
}