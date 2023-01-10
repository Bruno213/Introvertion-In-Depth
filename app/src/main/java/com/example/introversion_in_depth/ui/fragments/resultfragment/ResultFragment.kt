package com.example.introversion_in_depth.ui.fragments.resultfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentResultBinding
import com.example.introversion_in_depth.ui.ViewState

class ResultFragment : BaseFragment<FragmentResultBinding>(), View.OnClickListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentResultBinding
        get() = FragmentResultBinding::inflate

    override fun setup() {

    }

    override fun handleState(viewState: ViewState) {

    }

    override fun onClick(v: View?) {

    }
}