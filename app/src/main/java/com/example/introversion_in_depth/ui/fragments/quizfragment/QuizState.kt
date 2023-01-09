package com.example.introversion_in_depth.ui.fragments.quizfragment

import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.ui.ViewState

sealed class QuizState: ViewState() {
    object Finished: QuizState()

    data class QuestionLoaded(
        val question: String,
        val answer: Answer?,
        val questionCount: Int,
        val initialQuestion: Boolean = false
    ): QuizState()

    data class Error(val e: Throwable): QuizState()
}