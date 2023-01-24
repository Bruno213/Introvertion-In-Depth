package com.example.introversion_in_depth.ui.fragments.startFragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.interfaces.ViewStateHandler
import com.example.introversion_in_depth.datalayer.entities.entityrelation.QuizWithAnswers
import com.example.introversion_in_depth.datalayer.repository.QuizRepository
import com.example.introversion_in_depth.util.saveImage
import com.example.introversion_in_depth.util.screenShot
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File

class StartViewModel(
    private val view: ViewStateHandler,
    private val quizRepository: QuizRepository,
    private val file: File
): BaseViewModel<StartState, StartAction>() {

    private var count = 0
    private val answersList = ArrayList<QuizWithAnswers>()

    init {
        collect()
    }

    override fun collect() {
        state.onEach {
            view.handleState(it)
        }.launchIn(viewModelScope)
    }

    override fun process(action: StartAction) {
        CoroutineScope(Dispatchers.Default).launch {
            when (action) {
                StartAction.SetToIdle -> {
                    delay(300)
                    setState(StartState.Idle)
                }

                StartAction.PickLanguage -> {
                }

                StartAction.LoadQuiz -> {
                    setState(StartState.Loading)
                    delay(500)
                    setState(StartState.OpeningQuiz)
                }

                StartAction.LoadTestInfo -> {
                    setState(StartState.TestInfoLoaded)
                    process(StartAction.SetToIdle)
                }

                StartAction.LoadResults -> {
                    val quizCount = withContext(Dispatchers.Default)
                    { quizRepository.getValidQuizCount() }

                    if(quizCount == 0) {
                        setState(StartState.NoResults)
                        process(StartAction.SetToIdle)
                        return@launch
                    }

                    val results: List<QuizWithAnswers> = if(quizCount != count) {
                        count = quizCount

                        val answers = withContext(Dispatchers.Default)
                        { quizRepository.getQuizzesWithAnswers() }

                        answersList.clear()
                        answersList.addAll(answers)

                        answersList
                    } else {
                        answersList
                    }

                    setState(StartState.ResultsLoaded(results))
                    process(StartAction.SetToIdle)
                }

                is StartAction.ShareResult -> {
                   setState(
                       StartState.SharingResult(
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