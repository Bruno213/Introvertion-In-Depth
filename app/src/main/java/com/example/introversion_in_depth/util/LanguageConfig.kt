package com.example.introversion_in_depth.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import java.util.*

object LanguageConfig {
    private lateinit var res: Resources

    fun setLocale(context: Context, languageCode: String): Context {
        SharedPrefsHelper.store("chosenLang", languageCode)

        return updateResources(context, languageCode)
    }

//    fun getString(@StringRes id: Int): String {
//        return res.getString(id)
//    }
//
//    fun getString(@StringRes id: Int, vararg formatStrings : Any): String {
//        return res.getString(id, *formatStrings)
//    }


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
}