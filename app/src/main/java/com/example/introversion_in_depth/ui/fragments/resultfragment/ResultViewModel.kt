package com.example.introversion_in_depth.ui.fragments.resultfragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateHandler
import com.example.introversion_in_depth.data.dataholders.QuizResult
import com.example.introversion_in_depth.data.repository.QuizRepository
import com.example.introversion_in_depth.util.IntroversionMeter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultViewModel(
    private val view:ViewStateHandler,
    private val quizRepository: QuizRepository
    ): BaseViewModel<ResultState, ResultAction>() {

    init {
        collect()
    }

    override fun collect() {
        state.onEach {
            view.handleState(it)
        }.launchIn(viewModelScope)
    }

    override fun process(action: ResultAction) {
        CoroutineScope(Dispatchers.Default).launch {
            when(action) {
                is ResultAction.ReckonResult -> {
                    val quizWithAnswers = withContext(Dispatchers.Default)
                    { quizRepository.getQuizWithAnswers(action.quizId) }

                    val quizResult = QuizResult(
                        socialScore = IntroversionMeter.computeSocialScore(quizWithAnswers.answers),
                        thinkingScore = IntroversionMeter.computeThinkingScore(quizWithAnswers.answers),
                        anxiousScore = IntroversionMeter.computeAnxiousScore(quizWithAnswers.answers),
                        restrainedScore = IntroversionMeter.computeRestrainedScore(quizWithAnswers.answers)
                    )

                    val quiz = withContext(Dispatchers.Default)
                    { quizRepository.getQuiz(action.quizId) }

                    quizRepository.updateQuiz(quiz.copy(result = quizResult))
                    setState(ResultState.ResultReckoned(quizResult))
                }

                else ->{}
            }
        }
    }

    override fun clear() {
    }
}