package com.example.introversion_in_depth.ui.fragments.startFragment

import com.example.introversion_in_depth.data.entities.entityrelation.QuizWithAnswers
import com.example.introversion_in_depth.ui.ViewState

sealed class StartState: ViewState() {
    data class ResultsLoaded(val results: List<QuizWithAnswers>): StartState()
    object NoResults: StartState()
    object Loading: ViewState()
    data class Error(val e: Throwable): ViewState()
}