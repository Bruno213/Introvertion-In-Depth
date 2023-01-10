package com.example.introversion_in_depth.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.introversion_in_depth.data.dataholders.QuizResult

@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startDate: String = "",
    @Embedded
    val result: QuizResult = QuizResult()
)
