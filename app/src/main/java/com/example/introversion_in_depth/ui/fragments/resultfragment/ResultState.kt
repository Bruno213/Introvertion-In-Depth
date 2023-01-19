package com.example.introversion_in_depth.ui.fragments.resultfragment

import android.net.Uri
import com.example.introversion_in_depth.domain.datalayer.dataholders.QuizResult
import com.example.introversion_in_depth.ui.ViewState

sealed class ResultState: ViewState() {
    data class ResultReckoned(val data: QuizResult): ResultState()
    object Idle: ResultState()
    object LeavingResult : ViewState()
    data class SharingResult(val data: Uri?): ViewState()
    data class Error(val e: Throwable): ResultState()
}