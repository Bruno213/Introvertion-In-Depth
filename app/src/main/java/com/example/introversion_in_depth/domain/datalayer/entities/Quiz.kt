package com.example.introversion_in_depth.domain.datalayer.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.introversion_in_depth.domain.datalayer.dataholders.QuizResult

@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val code: String = "",
    @Embedded
    val result: QuizResult = QuizResult()
)
