package com.example.introversion_in_depth.data.repository

import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.data.entities.Quiz
import com.example.introversion_in_depth.data.entities.entityrelation.QuizWithAnswers
import com.example.introversion_in_depth.data.local.dao.QuizDao
import kotlinx.coroutines.flow.Flow

class QuizRepository(private val quizDao: QuizDao) {
    suspend fun insertQuiz(quiz: Quiz): Long {
        return quizDao.insertQuiz(quiz)
    }

    fun getQuiz(id: Int): Quiz {
        return quizDao.getQuiz(id)
    }

    fun getQuizWithAnswers(): Flow<List<QuizWithAnswers>> {
        return quizDao.getQuizWithAnswers()
    }

    fun getAnswer(id: Int): Answer {
        return quizDao.getAnswer(id)
    }

    fun insertAnswer(answer: Answer): Long {
        return quizDao.insertAnswer(answer)
    }
}