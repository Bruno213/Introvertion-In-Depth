package com.example.introversion_in_depth.base

import com.example.introversion_in_depth.ui.ViewState

interface ViewStateHandler {
    fun handleState(viewState: ViewState)
}