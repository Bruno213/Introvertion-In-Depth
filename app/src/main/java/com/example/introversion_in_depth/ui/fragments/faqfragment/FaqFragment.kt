package com.example.introversion_in_depth.ui.fragments.faqfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentFaqBinding
import com.example.introversion_in_depth.ui.ViewState


class FaqFragment : BaseFragment<FragmentFaqBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFaqBinding
        get() = FragmentFaqBinding::inflate

    override fun setup() {

    }

    override fun render(viewState: ViewState) {

    }
}