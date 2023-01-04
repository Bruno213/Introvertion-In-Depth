package com.example.introversion_in_depth.ui.fragments.startFragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateRenderer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StartViewModel(private val view:ViewStateRenderer): BaseViewModel<StartState, StartAction>() {

    override fun collect() {
        state.onEach {
            view.render(it)
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