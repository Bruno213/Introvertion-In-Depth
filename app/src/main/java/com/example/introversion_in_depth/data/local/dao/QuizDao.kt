package com.example.introversion_in_depth.data.local.dao

import androidx.room.*
import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.data.entities.Quiz
import com.example.introversion_in_depth.data.entities.entityrelation.QuizWithAnswers
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Query("SELECT * FROM quiz WHERE id == :quizId")
    suspend fun getQuiz(quizId: Int): Quiz

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: Quiz): Long

    @Update
    suspend fun updateQuiz(quiz: Quiz)

    @Delete
    suspend fun deleteQuiz(quiz: Quiz)

    @Transaction
    @Query("SELECT * FROM Quiz")
    suspend fun getQuizzesWithAnswers(): List<QuizWithAnswers>

    @Transaction
    @Query("SELECT * FROM Quiz WHERE id == :quizId")
    suspend fun getQuizWithAnswers(quizId: Int): QuizWithAnswers

    @Query("SELECT * FROM answer WHERE id == :id")
    fun getAnswer(id: Int): Answer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: Answer): Long

    @Update
    suspend fun updateAnswer(answer: Answer)

    @Query("DELETE FROM answer WHERE quizId == :quizId")
    suspend fun deleteAnswers(quizId: Int)

    @Query("SELECT count(*) from answer")
    fun getAnswersCount(): Int
}