package com.example.introversion_in_depth.data.repository

import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.data.entities.Quiz
import com.example.introversion_in_depth.data.entities.entityrelation.QuizWithAnswers
import com.example.introversion_in_depth.data.local.dao.QuizDao
import kotlinx.coroutines.flow.Flow

class QuizRepository(private val quizDao: QuizDao) {

    suspend fun getQuiz(quizId: Int): Quiz {
        return quizDao.getQuiz(quizId)
    }

    suspend fun insertQuiz(quiz: Quiz): Long {
        return quizDao.insertQuiz(quiz)
    }

    suspend fun updateQuiz(quiz: Quiz) {
        quizDao.updateQuiz(quiz)
    }

    suspend fun deleteQuiz(quiz: Quiz) {
        quizDao.deleteQuiz(quiz)
    }

    suspend fun getQuizzesWithAnswers(): List<QuizWithAnswers> {
        return quizDao.getQuizzesWithAnswers()
    }

    suspend fun getQuizWithAnswers(quizId: Int): QuizWithAnswers {
        return quizDao.getQuizWithAnswers(quizId)
    }
//
//    fun getAnswer(id: Int): Answer {
//        return quizDao.getAnswer(id)
//    }

    suspend fun insertAnswer(answer: Answer): Long {
        return quizDao.insertAnswer(answer)
    }

    suspend fun updateAnswer(answer: Answer) {
        quizDao.updateAnswer(answer)
    }

    suspend fun deleteAnswers(quizId: Int) {
        quizDao.deleteAnswers(quizId)
    }

//    fun getAnswerCount(): Int {
//        return quizDao.getAnswersCount()
//    }
}