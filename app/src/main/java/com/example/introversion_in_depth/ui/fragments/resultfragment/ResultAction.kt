package com.example.introversion_in_depth.ui.fragments.resultfragment

import com.example.introversion_in_depth.ui.MVIAction

sealed class ResultAction: MVIAction() {
    object CalculateResult: ResultAction()
    object LoadTypesDescription: ResultAction()
}