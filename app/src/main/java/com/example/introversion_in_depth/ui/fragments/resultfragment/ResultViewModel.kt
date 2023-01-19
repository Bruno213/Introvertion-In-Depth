package com.example.introversion_in_depth.ui.fragments.resultfragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.domain.contracts.BaseViewModel
import com.example.introversion_in_depth.domain.interfaces.ViewStateHandler
import com.example.introversion_in_depth.domain.datalayer.dataholders.QuizResult
import com.example.introversion_in_depth.domain.repository.QuizRepository
import com.example.introversion_in_depth.util.IntroversionMeter
import com.example.introversion_in_depth.util.saveImage
import com.example.introversion_in_depth.util.screenShot
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File

class ResultViewModel(
    private val view: ViewStateHandler,
    private val quizRepository: QuizRepository,
    private val file: File
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

                ResultAction.SetToIdle -> {
                    delay(200)
                    setState(ResultState.Idle)
                }

                ResultAction.LeaveResult -> {
                    delay(200)
                    setState(ResultState.LeavingResult)
                }

                is ResultAction.ShareResult -> {

                   setState(
                       ResultState.SharingResult(
                           saveImage(file, action.context, screenShot(action.view))
                       )
                   )
                }
            }
        }
    }

    override fun clear() {
    }
}