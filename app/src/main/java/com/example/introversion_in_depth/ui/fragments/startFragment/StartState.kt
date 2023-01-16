package com.example.introversion_in_depth.ui.fragments.startFragment

import android.net.Uri
import com.example.introversion_in_depth.data.entities.entityrelation.QuizWithAnswers
import com.example.introversion_in_depth.ui.ViewState

sealed class StartState: ViewState() {
    data class ResultsLoaded(val results: List<QuizWithAnswers>): StartState()
    object NoResults: StartState()
    object TestInfoLoaded: StartState()
    object Idle: StartState()
    object OpeningQuiz: StartState()
    object Loading: ViewState()
    data class SharingResult(val data: Uri?): ViewState()
//    data class Error(val e: Throwable): ViewState()
}