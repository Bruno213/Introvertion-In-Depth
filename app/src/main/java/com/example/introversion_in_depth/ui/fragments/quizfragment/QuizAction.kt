package com.example.introversion_in_depth.ui.fragments.quizfragment

import com.example.introversion_in_depth.ui.MVIAction

sealed class QuizAction: MVIAction() {
    data class InitQuiz(
        val questions: List<String>,
        val restore: Boolean = false
        ): QuizAction()
    data class MoveToNext(val userChoice: Pair<String, Int>): QuizAction()
    object MoveToPrevious: QuizAction()
    object LeaveQuiz: QuizAction()
    object ShowContinuationPopup: QuizAction()
}