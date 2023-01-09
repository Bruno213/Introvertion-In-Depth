package com.example.introversion_in_depth.base

import androidx.lifecycle.ViewModel
import com.example.introversion_in_depth.ui.MVIAction
import com.example.introversion_in_depth.ui.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<state: ViewState, mviAction: MVIAction> : ViewModel() {

    /** Application workflow
     *
     * User interacts with UI
     * UI listeners are triggered
     * ViewModel processEvent method is called passing the unwanted event to it
     * Necessary methods are called according to the event received
     * The methods called will fill up a ViewState object
     * ReducerMethod will be called after the new ViewState object is done and it will receive the new state as a parameter
     * ReducerMethod  will merge the old and the new ViewState and deliver it to the ObservableStateHolder
     *
     * processEvent -> Channel -> renderEffect
     **/

    private val _state = MutableStateFlow(ViewState())
    protected val state = _state.asStateFlow()

    protected abstract fun collect()

    abstract fun process(action: mviAction)

    protected fun setState(viewState: ViewState) {
        _state.value = viewState
    }

    protected abstract fun clear()

    override fun onCleared() {
        clear()
        super.onCleared()
    }
}