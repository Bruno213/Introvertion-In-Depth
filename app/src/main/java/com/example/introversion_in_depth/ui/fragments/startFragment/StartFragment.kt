package com.example.introversion_in_depth.ui.fragments.startFragment

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentStartBinding
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.ui.MainActivity
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.removeLinksUnderline
import com.example.introversion_in_depth.util.viewModelsFactory

class StartFragment: BaseFragment<FragmentStartBinding>(), View.OnClickListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStartBinding
        get() = FragmentStartBinding::inflate

    private val viewModel by viewModelsFactory{
        StartViewModel(this, (activity?.applicationContext as CustomApplication).appContainer.quizRepository)
    }

    override fun setup() {
        setupListeners()
        enableLink()
    }

    override fun handleState(viewState: ViewState) {

    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.dehaze.id -> (activity as MainActivity).toggleDrawer()

            binding.languagePicker.id -> {}
            binding.btnStartQuiz.id -> {
                findNavController().navigate(R.id.action_start_to_quizFragment)
            }
            binding.results.id -> {
            }
            binding.aboutTheTest.id -> {
                findNavController().navigate(R.id.action_start_to_faq)
            }
        }
    }

    private fun setupListeners() {
        binding.dehaze.setOnClickListener(this)
        binding.languagePicker.setOnClickListener(this)
        binding.btnStartQuiz.setOnClickListener(this)
        binding.results.setOnClickListener(this)
        binding.aboutTheTest.setOnClickListener(this)
    }

    private fun enableLink() {
        binding.linkToArticle.movementMethod = LinkMovementMethod.getInstance()
        binding.linkToArticle.removeLinksUnderline()
    }
}