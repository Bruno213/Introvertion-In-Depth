package com.example.introversion_in_depth.ui.fragments.quizfragment

import android.content.Context
import com.example.introversion_in_depth.domain.datalayer.entities.entityrelation.QuizWithAnswers
import com.example.introversion_in_depth.ui.MVIAction

sealed class QuizAction: MVIAction() {
    data class InitQuiz(
        val context: Context,
        val questions: List<String>
        ): QuizAction()
    data class RestoreQuiz(val quizAndAnswers: QuizWithAnswers): QuizAction()
    object ClearAndStart: QuizAction()
    object StartQuiz: QuizAction()
    data class MoveToNext(val userChoice: Pair<String, Int>): QuizAction()
    object MoveToPrevious: QuizAction()
    object LeaveQuiz: QuizAction()
}