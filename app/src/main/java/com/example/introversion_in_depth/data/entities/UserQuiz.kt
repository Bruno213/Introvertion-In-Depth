package com.example.introversion_in_depth.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserQuiz(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val startDate: String,
    val result: String
)
