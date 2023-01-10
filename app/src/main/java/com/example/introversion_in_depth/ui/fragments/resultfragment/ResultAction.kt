package com.example.introversion_in_depth.ui.fragments.resultfragment

import com.example.introversion_in_depth.ui.MVIAction

sealed class ResultAction: MVIAction() {
    data class ReckonResult(val quizId: Int): ResultAction()
    object ShareResult: ResultAction()
}