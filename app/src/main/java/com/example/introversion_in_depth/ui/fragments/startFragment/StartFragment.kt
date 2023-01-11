package com.example.introversion_in_depth.ui.fragments.startFragment

import android.app.Dialog
import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentStartBinding
import com.example.introversion_in_depth.databinding.ResultsLayoutBinding
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
        when(viewState) {
            is StartState.ResultsLoaded -> {
                val dialogResults = Dialog(requireContext(), R.style.NewDialog)
                val inflater = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val bind = ResultsLayoutBinding.inflate(inflater)
                dialogResults.setContentView(bind.root)
                dialogResults.setCancelable(false)

                bind.root.setOnClickListener {
                    dialogResults.dismiss()
                    viewModel.process(StartAction.SetToIdle)
                }

                bind.btnClose.setOnClickListener {
                    dialogResults.dismiss()
                    viewModel.process(StartAction.SetToIdle)
                }

                dialogResults.show()
            }
        }
    }

    private fun setupListeners() {
        binding.dehaze.setOnClickListener(this)
//        binding.languagePicker.setOnClickListener(this)
        binding.btnStartQuiz.setOnClickListener(this)
        binding.results.setOnClickListener(this)
        binding.aboutTheTest.setOnClickListener(this)
    }

    private fun enableLink() {
        binding.linkToArticle.movementMethod = LinkMovementMethod.getInstance()
        binding.linkToArticle.removeLinksUnderline()
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.dehaze.id -> (activity as MainActivity).toggleDrawer()

//            binding.languagePicker.id -> {}
            binding.btnStartQuiz.id -> {
                findNavController().navigate(R.id.action_start_to_quizFragment)
            }

            binding.results.id -> viewModel.process(StartAction.LoadResults)

            binding.aboutTheTest.id -> {
                findNavController().navigate(R.id.action_start_to_faq)
            }
        }
    }
}