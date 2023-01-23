package com.example.introversion_in_depth.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.annotation.StringRes
import java.util.*

object LanguageConfig {
    private var resources: Resources? = null
    private val res get() = requireNotNull(resources)

    fun setLocale(context: Context, languageCode: String) {
        SharedPrefsHelper.store("chosenLang", languageCode)

        resources = updateResources(context, languageCode).resources
    }

    fun getString(@StringRes id: Int): String {
        return res.getString(id)
    }

    fun getString(@StringRes id: Int, vararg formatStrings : Any): String {
        return res.getString(id, *formatStrings)
    }

    fun getStringArray(questions: Int): Array<out String> {
        return res.getStringArray(questions)
    }

    private fun updateResources(context: Context, languageCode: String): Context {
        val configuration = context.resources.configuration

        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }

        return context.createConfigurationContext(configuration)
    }

    fun clearResources() {
        resources = null
    }
}