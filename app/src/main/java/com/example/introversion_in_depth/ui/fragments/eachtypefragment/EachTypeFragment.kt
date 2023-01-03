package com.example.introversion_in_depth.ui.fragments.eachtypefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentEachTypeBinding


class EachTypeFragment : BaseFragment<FragmentEachTypeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEachTypeBinding
        get() = FragmentEachTypeBinding::inflate

    override fun setup() {

    }
}