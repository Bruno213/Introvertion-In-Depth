package com.example.introversion_in_depth.ui.fragments.resultfragment

import android.content.Context
import android.view.View
import com.example.introversion_in_depth.ui.MVIAction

sealed class ResultAction: MVIAction() {
    data class ReckonResult(val quizId: Int): ResultAction()
    object LeaveResult: ResultAction()
    object SetToIdle: ResultAction()
    data class ShareResult(
        val view: View,
        val context: Context
        ): ResultAction()
}