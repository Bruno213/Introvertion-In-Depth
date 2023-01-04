package com.example.introversion_in_depth.ui.fragments.quizfragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateRenderer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class QuizViewModel(private val view: ViewStateRenderer): BaseViewModel<QuizState, QuizAction>() {

    init {
        collect()
    }

    override fun collect() {
        state.onEach {
            view.render(it)
        }.launchIn(viewModelScope)
    }

    override fun process(action: QuizAction) {
    }

    override fun clear() {
    }
}