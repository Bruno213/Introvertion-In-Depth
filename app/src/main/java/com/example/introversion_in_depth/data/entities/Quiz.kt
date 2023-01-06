package com.example.introversion_in_depth.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startDate: String = "",
    val result: String = ""
)
