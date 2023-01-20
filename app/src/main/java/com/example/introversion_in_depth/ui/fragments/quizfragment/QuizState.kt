package com.example.introversion_in_depth.ui.fragments.quizfragment

import android.app.Dialog
import com.example.introversion_in_depth.domain.datalayer.entities.Answer
import com.example.introversion_in_depth.domain.datalayer.entities.entityrelation.QuizWithAnswers
import com.example.introversion_in_depth.ui.ViewState

sealed class QuizState: ViewState() {
    object Finished: QuizState()

    data class QuestionLoaded(
        val question: String,
        val answer: Answer?,
        val questionCount: Int,
        val initialQuestion: Boolean = false
    ): QuizState()

    data class ContinuationPopupLoaded(
        val quizAndAnswers: QuizWithAnswers
    ): QuizState()

    object Loading: QuizState()
    object Idle: QuizState()
    object LeavingQuiz: QuizState()

    data class Error(val e: Throwable): QuizState()
}