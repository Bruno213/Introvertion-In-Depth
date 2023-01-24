package com.example.introversion_in_depth.datalayer.repository

import com.example.introversion_in_depth.datalayer.entities.Answer
import com.example.introversion_in_depth.datalayer.entities.Quiz
import com.example.introversion_in_depth.datalayer.entities.entityrelation.QuizWithAnswers
import com.example.introversion_in_depth.datalayer.local.dao.QuizDao

class QuizRepository(private val quizDao: QuizDao) {
    suspend fun getQuiz(quizId: Int): Quiz {
        return quizDao.getQuiz(quizId)
    }

    suspend fun getUnfinishedQuizWithAnswers(): QuizWithAnswers? {
        return quizDao.getUnfinishedQuizWithAnswers()
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

    suspend fun getQuizCount(): Int {
        return quizDao.getQuizCount()
    }

    suspend fun getValidQuizCount(): Int {
        return quizDao.getValidQuizCount()
    }

    suspend fun getQuizzesWithAnswers(): List<QuizWithAnswers> {
        return quizDao.getQuizzesWithAnswers()
    }

    suspend fun getQuizWithAnswers(quizId: Int): QuizWithAnswers {
        return quizDao.getQuizWithAnswers(quizId)
    }

    suspend fun insertAnswer(answer: Answer): Long {
        return quizDao.insertAnswer(answer)
    }

    suspend fun updateAnswer(answer: Answer) {
        quizDao.updateAnswer(answer)
    }

    suspend fun deleteAnswers(quizId: Int) {
        quizDao.deleteAnswers(quizId)
    }

    fun getAnswerCount(quizId: Int): Int {
        return quizDao.getAnswersCount(quizId)
    }
}