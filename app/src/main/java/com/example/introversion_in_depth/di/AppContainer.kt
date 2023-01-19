package com.example.introversion_in_depth.di

import android.content.Context
import androidx.room.Room
import com.example.introversion_in_depth.domain.datalayer.local.AppDatabase
import com.example.introversion_in_depth.domain.repository.QuizRepository
import java.io.File

class AppContainer(applicationContext: Context) {
    val quizRepository by lazy {
        val appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "introversionInDepth")
            .fallbackToDestructiveMigration()
            .build()

        QuizRepository(appDatabase.getQuizDao())
    }

    val file: File by lazy {
        File(applicationContext.cacheDir, "images")
    }
}