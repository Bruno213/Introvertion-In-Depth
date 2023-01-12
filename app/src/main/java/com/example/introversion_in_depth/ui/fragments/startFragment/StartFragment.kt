package com.example.introversion_in_depth.ui.fragments.startFragment

import android.app.Dialog
import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.data.dataholders.QuizResult
import com.example.introversion_in_depth.databinding.FragmentStartBinding
import com.example.introversion_in_depth.databinding.ResultsLayoutBinding
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.ui.MainActivity
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.IntroversionMeter
import com.example.introversion_in_depth.util.removeLinksUnderline
import com.example.introversion_in_depth.util.viewModelsFactory
import com.google.android.material.snackbar.Snackbar

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

    /**
     * Save list with answers
     * when requesting answers from db, check answers count to see if count has changed
     * Update variable that holds the list only if the count has changed
     * when loading results dialog, get list and adapter from viewModel and bind adapter to spinner
     *
     * **/

    override fun handleState(viewState: ViewState) {
        when (viewState) {
            is StartState.ResultsLoaded -> {
                loadResultsDialog(viewState)
            }

            StartState.NoResults -> {
                Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),
                    R.string.no_results, Snackbar.LENGTH_SHORT).show()
                viewModel.process(StartAction.SetToIdle)
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

    private fun loadResultsDialog(viewState: StartState.ResultsLoaded) {
        val dialogResults = Dialog(requireContext(), R.style.NewDialog)
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bind = ResultsLayoutBinding.inflate(inflater)
        dialogResults.setContentView(bind.root)
        dialogResults.setCancelable(false)


        val results = viewState.results
        val items = results.map { "Quiz ${it.quiz.code}" }
        val adapter =
            ArrayAdapter(requireContext(), R.layout.custom_spinner_item, items)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        bind.resultsSpinner.adapter = adapter

        bind.resultsSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                loadResultText(bind, results[position].quiz.result)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        loadResultText(bind, results[0].quiz.result)

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

    private fun loadResultText(bind: ResultsLayoutBinding, result: QuizResult) {
        bind.socialScore.text  = resources.getString(R.string.score, result.socialScore)
        bind.socialLevel.text = IntroversionMeter.checkSocialLevel(resources, result.socialScore)

        bind.thinkingScore.text  = resources.getString(R.string.score, result.thinkingScore)
        bind.thinkingLevel.text = IntroversionMeter.checkThinkingLevel(resources, result.thinkingScore)

        bind.anxiousScore.text  = resources.getString(R.string.score, result.anxiousScore)
        bind.anxiousLevel.text = IntroversionMeter.checkAnxiousLevel(resources, result.anxiousScore)

        bind.restrainedScore.text  = resources.getString(R.string.score, result.restrainedScore)
        bind.restrainedLevel.text = IntroversionMeter.checkRestrainedLevel(resources, result.restrainedScore)
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