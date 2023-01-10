package com.example.introversion_in_depth.ui.fragments.resultfragment

import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.ui.ViewState

sealed class ResultState: ViewState() {
    object Finished: ResultState()

    data class QuestionLoaded(
        val question: String,
        val answer: Answer?,
        val questionCount: Int,
        val initialQuestion: Boolean = false
    ): ResultState()

    data class Error(val e: Throwable): ResultState()
}