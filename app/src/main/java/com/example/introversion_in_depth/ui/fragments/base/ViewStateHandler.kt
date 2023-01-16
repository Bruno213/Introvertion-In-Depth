package com.example.introversion_in_depth.ui.fragments.base

import com.example.introversion_in_depth.ui.ViewState

interface ViewStateHandler {
    fun handleState(viewState: ViewState)
}