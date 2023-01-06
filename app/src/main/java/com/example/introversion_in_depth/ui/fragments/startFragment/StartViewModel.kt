package com.example.introversion_in_depth.ui.fragments.startFragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateHandler
import com.example.introversion_in_depth.data.repository.QuizRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StartViewModel(
    private val view:ViewStateHandler,
    private val quizRepository: QuizRepository
    ): BaseViewModel<StartState, StartAction>() {

    override fun collect() {
        state.onEach {
            view.handleState(it)
        }.launchIn(viewModelScope)
    }

    override fun process(action: StartAction) {
        when(action) {
            is StartAction.PickLanguage -> {

            }

           else ->{}
        }
    }

    override fun clear() {
    }
}