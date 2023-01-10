package com.example.introversion_in_depth.ui.fragments.resultfragment

import com.example.introversion_in_depth.data.dataholders.QuizResult
import com.example.introversion_in_depth.ui.ViewState

sealed class ResultState: ViewState() {
    data class ResultReckoned(val data: QuizResult): ResultState()
    data class Error(val e: Throwable): ResultState()
}