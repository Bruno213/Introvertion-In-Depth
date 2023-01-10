package com.example.introversion_in_depth.data.local.dao

import androidx.room.*
import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.data.entities.Quiz
import com.example.introversion_in_depth.data.entities.entityrelation.QuizWithAnswers
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: Quiz): Long

    @Query("SELECT * FROM Quiz WHERE id == :id")
    fun getQuiz(id: Int): Quiz

    @Transaction
    @Query("SELECT * FROM Quiz WHERE id == :quizId")
    suspend fun getQuizWithAnswers(quizId: Int): QuizWithAnswers

    @Query("SELECT * FROM answer WHERE id == :id")
    fun getAnswer(id: Int): Answer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: Answer): Long

    @Update
    fun updateAnswer(answer: Answer)

    @Query("SELECT count(*) from answer")
    fun getAnswersCount(): Int
}