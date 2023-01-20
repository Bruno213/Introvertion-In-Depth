package com.example.introversion_in_depth.util

import android.content.SharedPreferences

object SharedPrefsHelper {
    private lateinit var sharedPrefs: SharedPreferences

    fun setSharedPrefs(sharedPreferences: SharedPreferences) {
        sharedPrefs = sharedPreferences
    }

    fun get(key: String, default: String?): String? {
        return sharedPrefs.getString(key, default)
    }

    fun get(key: String, default: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, default)
    }

    fun get(key: String, default: Int): Int {
        return sharedPrefs.getInt(key, default)
    }

    fun store(key: String, value: String) {
        sharedPrefs.edit().putString(key, value).apply()
    }

    fun store(key: String, value: Boolean) {
        sharedPrefs.edit().putBoolean(key, value).apply()
    }

    fun store(key: String, value: Int) {
        sharedPrefs.edit().putInt(key, value).apply()
    }
}