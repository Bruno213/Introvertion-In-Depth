package com.example.introversion_in_depth.ui.fragments.resultfragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateHandler
import com.example.introversion_in_depth.data.dataholders.QuizResult
import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.data.repository.QuizRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ResultViewModel(
    private val view:ViewStateHandler,
    private val quizRepository: QuizRepository
    ): BaseViewModel<ResultState, ResultAction>() {

    init {
        collect()
    }

    override fun collect() {
        state.onEach {
            view.handleState(it)
        }.launchIn(viewModelScope)
    }

    override fun process(action: ResultAction) {
        CoroutineScope(Dispatchers.Default).launch {
            when(action) {
                is ResultAction.ReckonResult -> {
                    val quizWithAnswers = withContext(Dispatchers.Default)
                    { quizRepository.getQuizWithAnswers(action.quizId) }

                    val quizResult = QuizResult(
                        socialScore = computeSocialScore(quizWithAnswers.answers),
                        thinkingScore = computeThinkingScore(quizWithAnswers.answers),
                        anxiousScore = computeAnxiousScore(quizWithAnswers.answers),
                        restrainedScore = computeRestrainedScore(quizWithAnswers.answers)
                    )

                    val quiz = withContext(Dispatchers.Default)
                    { quizRepository.getQuiz(action.quizId) }

                    quizRepository.updateQuiz(quiz.copy(result = quizResult))
                    setState(ResultState.ResultReckoned(quizResult))
                }

                else ->{}
            }
        }
    }

    fun detectStrongerType(array: Array<Int>): Int {
        val greaterValue = array.max()

        for(n in array) {
            if(greaterValue == n) return -1
        }

        return array.indexOf(greaterValue)
    }

    private fun computeSocialScore(answers: List<Answer>): Int {
        var score = 0

        for(i in 0 until 10) {
            score += when(i) {
                1, 3, 6 -> recode(answers[i].choiceValue)
                else -> answers[i].choiceValue
            }
        }

        return score
    }

    private fun computeThinkingScore(answers: List<Answer>): Int {
        var score = 0

        for(i in 10 until 20) {
            score += when(i) {
                14 -> recode(answers[i].choiceValue)
                else -> answers[i].choiceValue
            }
        }

        return score
    }

    private fun computeAnxiousScore(answers: List<Answer>): Int {
        var score = 0

        for(i in 20 until 30) {
            score += when(i) {
                23, 25, 26 -> recode(answers[i].choiceValue)
                else -> answers[i].choiceValue
            }
        }

        return score
    }

    private fun computeRestrainedScore(answers: List<Answer>): Int {
        var score = 0

        for(i in 30 until 40) {
            score += when(i) {
                30, 31, 33, 34, 35, 36, 37, 38 -> recode(answers[i].choiceValue)
                else -> answers[i].choiceValue
            }
        }

        return score
    }

    private fun recode(value: Int): Int {
        return when(value) {
            1 -> 5
            2 -> 4
            4 -> 2
            5 -> 1
            else -> value
        }
    }

    override fun clear() {
    }
}