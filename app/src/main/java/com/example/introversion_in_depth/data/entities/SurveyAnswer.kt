package com.example.introversion_in_depth.data.entities

import androidx.room.PrimaryKey


data class SurveyAnswer(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val quizId: Int,
    val code: String,
    val choice: String,
    val choiceValue: Int
)
