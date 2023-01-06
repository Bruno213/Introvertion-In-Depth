package com.example.introversion_in_depth.ui.fragments.quizfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentQuizBinding
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.viewModelsFactory

class QuizFragment : BaseFragment<FragmentQuizBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentQuizBinding
        get() = FragmentQuizBinding::inflate

    private val viewModel by viewModelsFactory {
        QuizViewModel(this, (activity?.applicationContext as CustomApplication).appContainer.quizRepository)
    }

    override fun handleState(viewState: ViewState) {
        when(viewState) {
            QuizState.Idle -> {

            }

            is QuizState.NextQuestion -> {

            }
        }
    }

    override fun setup() {
        setListeners()

        val questionsArray = resources.getStringArray(R.array.questions).asList()
        viewModel.process(QuizAction.StartNewQuiz(questionsArray))
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->

        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnNext.id -> {
                viewModel.process(QuizAction.ToNextQuestion)
            }

            binding.btnBack.id -> {
                viewModel.process(QuizAction.ToPreviousQuestion)
            }
        }
    }
}