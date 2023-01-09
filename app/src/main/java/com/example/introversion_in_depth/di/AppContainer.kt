package com.example.introversion_in_depth.di

import android.content.Context
import androidx.room.Room
import com.example.introversion_in_depth.data.local.AppDatabase
import com.example.introversion_in_depth.data.repository.QuizRepository

class AppContainer(applicationContext: Context) {
    val quizRepository by lazy {
        val appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "introversionInDepth")
            .fallbackToDestructiveMigration()
            .build()

        QuizRepository(appDatabase.getQuizDao())
    }
}