package com.example.introversion_in_depth.domain.datalayer.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "answer")
data class Answer(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val quizId: Int,
    val choice: String = "",
    val choiceValue: Int = -1
)
