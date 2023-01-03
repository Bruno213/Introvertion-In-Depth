package com.example.introversion_in_depth.ui.fragments.faqfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentFaqBinding


class FaqFragment : BaseFragment<FragmentFaqBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFaqBinding
        get() = FragmentFaqBinding::inflate

    override fun setup() {

    }
}