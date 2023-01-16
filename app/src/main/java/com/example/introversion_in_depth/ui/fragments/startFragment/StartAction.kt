package com.example.introversion_in_depth.ui.fragments.startFragment

import android.content.Context
import android.view.View
import com.example.introversion_in_depth.ui.MVIAction

sealed class StartAction: MVIAction() {
    object SetToIdle: StartAction()
    object PickLanguage: StartAction()
    object LoadQuiz: StartAction()
    object LoadResults: StartAction()
    object LoadTestInfo: StartAction()
    data class ShareResult(
        val view: View,
        val context: Context
    ): StartAction()
}
