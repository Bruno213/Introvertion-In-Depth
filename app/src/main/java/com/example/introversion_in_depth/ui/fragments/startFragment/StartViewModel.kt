package com.example.introversion_in_depth.ui.fragments.startFragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateHandler
import com.example.introversion_in_depth.data.entities.entityrelation.QuizWithAnswers
import com.example.introversion_in_depth.data.repository.QuizRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StartViewModel(
    private val view:ViewStateHandler,
    private val quizRepository: QuizRepository
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
                    delay(200)
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
                    { quizRepository.getQuizCount() }

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
            }
        }
    }

    override fun clear() {
    }
}