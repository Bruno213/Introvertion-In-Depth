package com.example.introversion_in_depth.ui

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.introversion_in_depth.domain.contracts.BaseActivity
import com.example.introversion_in_depth.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val navController  by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navHostFragment.navController
//        Navigation.findNavController(this, binding.fragmentContainerView.id)
    }

    private val valueAnimator = ValueAnimator.ofFloat(0f, 90f, 180f, 270f, 360f)

    override fun setup() {
        binding.navView.setupWithNavController(navController)
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

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.root)
    }

    override fun onBackPressed() {
        if (binding.root.isDrawerOpen(GravityCompat.START)) {
            binding.root.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}