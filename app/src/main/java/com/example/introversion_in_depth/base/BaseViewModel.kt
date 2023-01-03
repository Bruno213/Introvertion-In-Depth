package com.example.introversion_in_depth.base

import androidx.lifecycle.ViewModel
import com.example.introversion_in_depth.data.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<state: State>: ViewModel() {

    /** Application workflow
     *
     * User interacts with UI
     * UI listeners are triggered
     * ViewModel processEvent method is called passing the unwanted event to it
     * Necessary methods are called according to the event received
     * The methods called will fill up a State object
     * ReducerMethod will be called after the new State object is done and it will receive the new state as a parameter
     * ReducerMethod  will merge the old and the new State and deliver it to the ObservableStateHolder
     **/

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    abstract fun collectState()

    abstract fun processEvent()
}