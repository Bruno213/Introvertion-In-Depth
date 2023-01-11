package com.example.introversion_in_depth.ui.fragments.startFragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateHandler
import com.example.introversion_in_depth.data.repository.QuizRepository
import com.example.introversion_in_depth.ui.ViewState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StartViewModel(
    private val view:ViewStateHandler,
    private val quizRepository: QuizRepository
): BaseViewModel<StartState, StartAction>() {

    init {
        collect()
    }

    override fun collect() {
        state.onEach {
            view.handleState(it)
        }.launchIn(viewModelScope)
    }

    fun startCollection() {
        collect()
    }

    override fun process(action: StartAction) {
        CoroutineScope(Dispatchers.Default).launch {
            when (action) {
                StartAction.SetToIdle -> {
                    setState(ViewState())
                }

                StartAction.PickLanguage -> {

                }

                StartAction.LoadResults -> {
                    val results = withContext(Dispatchers.Default)
                    { quizRepository.getQuizzesWithAnswers() }

                    setState(StartState.ResultsLoaded(results))
                }
            }
        }
    }

    override fun clear() {
    }
}