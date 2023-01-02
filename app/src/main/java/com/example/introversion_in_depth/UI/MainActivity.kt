package com.example.introversion_in_depth.UI

import android.view.LayoutInflater
import com.example.introversion_in_depth.base.BaseActivity
import com.example.introversion_in_depth.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate


    override fun setup() {

    }
}