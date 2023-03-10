package com.example.introversion_in_depth.datalayer.entities.entityrelation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.introversion_in_depth.datalayer.entities.Answer
import com.example.introversion_in_depth.datalayer.entities.Quiz

data class QuizWithAnswers(
    @Embedded val quiz : Quiz,
    @Relation(
        parentColumn = "id",
        entityColumn = "quizId"
    ) val answers: List<Answer>
)
