package com.example.introversion_in_depth.ui.fragments.resultfragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateHandler
import com.example.introversion_in_depth.data.repository.QuizRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ResultViewModel(
    private val view:ViewStateHandler,
    private val quizRepository: QuizRepository
    ): BaseViewModel<ResultState, ResultAction>() {

    override fun collect() {
        state.onEach {
            view.handleState(it)
        }.launchIn(viewModelScope)
    }

    override fun process(action: ResultAction) {
        when(action) {

           else ->{}
        }
    }

    override fun clear() {
    }
}