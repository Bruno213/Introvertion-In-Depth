package com.example.introversion_in_depth.util

import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.datalayer.entities.Answer

object IntroversionMeter {
    fun checkSocialLevel(score: Int): String {
        return LanguageConfig.getString(
            R.string.level,
            LanguageConfig.getString(
                when {
                    score < 24 -> R.string.low
                    score <= 36 -> R.string.average
                    else -> R.string.high
                }
            ))
    }

    fun checkThinkingLevel(score: Int): String {
        return LanguageConfig.getString(
            R.string.level,
            LanguageConfig.getString(
                when {
                    score < 28 -> R.string.low
                    score <= 40 -> R.string.average
                    else -> R.string.high
                }
            ))
    }

    fun checkAnxiousLevel(score: Int): String {
        return LanguageConfig.getString(
            R.string.level,
            LanguageConfig.getString(
                when {
                    score < 23 -> R.string.low
                    score <= 37 -> R.string.average
                    else -> R.string.high
                }
            ))
    }

    fun checkRestrainedLevel(score: Int): String {
        return LanguageConfig.getString(
            R.string.level,
            LanguageConfig.getString(
                when {
                    score < 25 -> R.string.low
                    score <= 37 -> R.string.average
                    else -> R.string.high
                }
            ))
    }

    fun computeSocialScore(answers: List<Answer>): Int {
        var score = 0

        for(i in 0 until 10) {
            score += when(i) {
                1, 3, 6 -> recode(answers[i].choiceValue)
                else -> answers[i].choiceValue
            }
        }

        return score
    }

    fun computeThinkingScore(answers: List<Answer>): Int {
        var score = 0

        for(i in 10 until 20) {
            score += when(i) {
                14 -> recode(answers[i].choiceValue)
                else -> answers[i].choiceValue
            }
        }

        return score
    }

    fun computeAnxiousScore(answers: List<Answer>): Int {
        var score = 0

        for(i in 20 until 30) {
            score += when(i) {
                23, 25, 26 -> recode(answers[i].choiceValue)
                else -> answers[i].choiceValue
            }
        }

        return score
    }

    fun computeRestrainedScore(answers: List<Answer>): Int {
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
}