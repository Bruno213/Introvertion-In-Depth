package com.example.introversion_in_depth.domain.datalayer.local.dao

import androidx.room.*
import com.example.introversion_in_depth.domain.datalayer.entities.Answer
import com.example.introversion_in_depth.domain.datalayer.entities.Quiz
import com.example.introversion_in_depth.domain.datalayer.entities.entityrelation.QuizWithAnswers

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

    @Query("SELECT count(*) FROM quiz")
    suspend fun getQuizCount(): Int

    @Transaction
    @Query("SELECT * FROM Quiz WHERE socialScore != 0")
    suspend fun getQuizzesWithAnswers(): List<QuizWithAnswers>

    @Transaction
    @Query("SELECT * FROM Quiz WHERE id == :quizId")
    suspend fun getQuizWithAnswers(quizId: Int): QuizWithAnswers

    @Transaction
    @Query("SELECT * FROM quiz WHERE socialScore == 0")
    suspend fun getUnfinishedQuizWithAnswers(): QuizWithAnswers?

    @Query("SELECT * FROM answer WHERE id == :id")
    fun getAnswer(id: Int): Answer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: Answer): Long

    @Update
    suspend fun updateAnswer(answer: Answer)

    @Query("DELETE FROM answer WHERE quizId == :quizId")
    suspend fun deleteAnswers(quizId: Int)

    @Query("SELECT count(*) from answer WHERE quizId == :quizId")
    fun getAnswersCount(quizId: Int): Int
}