package com.example.introversion_in_depth.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "answer")
data class Answer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val quizId: Int = -1,
    val code: String = "",
    val choice: String = "",
    val choiceValue: Int = -1
)
