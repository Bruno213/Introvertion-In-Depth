package com.example.introversion_in_depth.datalayer.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.introversion_in_depth.datalayer.entities.Answer
import com.example.introversion_in_depth.datalayer.entities.Quiz
import com.example.introversion_in_depth.datalayer.local.dao.QuizDao

@Database(version = 1, exportSchema = false, entities = [Quiz::class, Answer::class])
abstract class AppDatabase: RoomDatabase() {
    abstract fun getQuizDao(): QuizDao

//    companion object {
//        @Volatile private var instance: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase =
//            instance ?: synchronized(this) {instance ?: buildDatabase(context).also { instance = it }}
//
//        private fun buildDatabase(appContext: Context) =
//            Room.databaseBuilder(appContext, AppDatabase::class.java, "introversionInDepth")
//                .fallbackToDestructiveMigration()
//                .build()
//    }
}