package com.example.introversion_in_depth.ui.fragments.quizfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentQuizBinding
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.viewModelsFactory

class QuizFragment : BaseFragment<FragmentQuizBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentQuizBinding
        get() = FragmentQuizBinding::inflate

    private val viewModel by viewModelsFactory { QuizViewModel(this) }

    override fun setup() {

    }

    override fun render(viewState: ViewState) {

    }
}