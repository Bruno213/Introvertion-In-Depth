package com.example.introversion_in_depth.ui.fragments.startFragment

import com.example.introversion_in_depth.ui.ViewState

sealed class StartState: ViewState() {
    data class Dummy(val dummyData: String): ViewState()

    object Loading: ViewState()

    data class Error(val e: Throwable): ViewState()
}