package com.example.introversion_in_depth.ui.fragments.quizfragment

import com.example.introversion_in_depth.ui.MVIAction

sealed class QuizAction: MVIAction() {
    data class StartNewQuiz(val questions: List<String>): QuizAction()
    data class MoveToNext(val userChoice: Pair<String, Int>): QuizAction()
    object MoveToPrevious: QuizAction()
    object DeleteQuiz: QuizAction()
}