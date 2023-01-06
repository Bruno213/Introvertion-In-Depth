package com.example.introversion_in_depth.data.entities.entityrelation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.data.entities.Quiz

data class QuizWithAnswers(
    @Embedded val quiz : Quiz,
    @Relation(
        parentColumn = "id",
        entityColumn = "quizId"
    ) val answers: List<Answer>
)
