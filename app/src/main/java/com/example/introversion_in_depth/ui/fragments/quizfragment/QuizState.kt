package com.example.introversion_in_depth.ui.fragments.quizfragment

import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.ui.ViewState

sealed class QuizState: ViewState() {
    object Idle: QuizState()
    data class NextQuestion(val data: Answer): QuizState()
    data class Error(val e: Throwable): QuizState()
}