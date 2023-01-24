package com.example.introversion_in_depth.interfaces

import com.example.introversion_in_depth.ui.ViewState

interface ViewStateHandler {
    fun handleState(viewState: ViewState)
}