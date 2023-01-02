package com.example.introversion_in_depth.UI.fragments.StartScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentStartScreenBinding


class StartScreen: BaseFragment<FragmentStartScreenBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStartScreenBinding
        get() = FragmentStartScreenBinding::inflate


    override fun setup() {

    }
}