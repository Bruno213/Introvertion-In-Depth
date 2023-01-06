package com.example.introversion_in_depth.ui.fragments.quizfragment

import com.example.introversion_in_depth.ui.MVIAction

sealed class QuizAction: MVIAction() {
    object GetQuiz: QuizAction()
    data class StartNewQuiz(val questions: List<String>): QuizAction()
    object ToNextQuestion: QuizAction()
    object ToPreviousQuestion: QuizAction()
}