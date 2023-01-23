package com.example.introversion_in_depth.ui

import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import com.example.introversion_in_depth.databinding.ActivityMainBinding
import com.example.introversion_in_depth.domain.contracts.BaseActivity
import com.example.introversion_in_depth.util.LanguageConfig
import com.example.introversion_in_depth.util.SharedPrefsHelper

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val valueAnimator = ValueAnimator.ofFloat(0f, 90f, 180f, 270f, 360f)

    override fun setup() {
        SharedPrefsHelper.setSharedPrefs(getSharedPreferences("introversionInDepth", MODE_PRIVATE))
        val chosenLang = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SharedPrefsHelper.get("chosenLang", null)?: resources.configuration.locales[0].language
        } else {
            SharedPrefsHelper.get("chosenLang", null)?: resources.configuration.locale.language
        }

        LanguageConfig.setLocale(this, chosenLang)
    }

    fun showLoading() {
        valueAnimator.apply {
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            start()
        }.addUpdateListener {
            val animatedValue = it.animatedValue as Float
            binding.loading.loadingIcon.rotation = animatedValue
        }
        binding.loading.root.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.loading.root.visibility = View.GONE

        if(valueAnimator.isRunning) {
            valueAnimator.pause()
            valueAnimator.removeAllUpdateListeners()
        }
    }
}