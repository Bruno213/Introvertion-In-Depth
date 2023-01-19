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
import android.widget.TextView
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.domain.contracts.BaseFragment
import com.example.introversion_in_depth.domain.datalayer.dataholders.QuizResult
import com.example.introversion_in_depth.databinding.FragmentStartBinding
import com.example.introversion_in_depth.databinding.ResultsLayoutBinding
import com.example.introversion_in_depth.databinding.TestInfoLayoutBinding
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.ui.MainActivity
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.IntroversionMeter
import com.example.introversion_in_depth.util.removeLinksUnderline
import com.example.introversion_in_depth.util.shareImageUri
import com.example.introversion_in_depth.util.viewModelsFactory
import com.google.android.material.snackbar.Snackbar

class StartFragment: BaseFragment<FragmentStartBinding>(), View.OnClickListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStartBinding
        get() = FragmentStartBinding::inflate

    private val viewModel by viewModelsFactory {
        val appContext = (activity?.applicationContext as CustomApplication)
        StartViewModel(this, appContext.appContainer.quizRepository, appContext.appContainer.file)
    }

    override fun setup() {
        setupListeners()
        enableLink(binding.linkToArticle)
    }

    override fun handleState(viewState: ViewState) {
        when (viewState) {
            is StartState.ResultsLoaded -> {
                loadResultsDialog(viewState)
            }

            StartState.NoResults -> {
                Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),
                    R.string.no_results, Snackbar.LENGTH_SHORT).show()
            }

            StartState.TestInfoLoaded -> {
                loadTestInfoDialog()
            }

            StartState.OpeningQuiz -> {
                val navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.fade_in)
                    .build()

                findNavController().navigate(R.id.action_start_to_quizFragment, null, navOptions)
            }

            is StartState.SharingResult -> {
                viewState.data?.let {
                    shareImageUri(requireContext(), it)
                }
            }

            StartState.Loading -> {
                (activity as MainActivity).showLoading()
            }

            StartState.Idle -> {
                (activity as MainActivity).hideLoading()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.process(StartAction.SetToIdle)
    }

    private fun setupListeners() {
        binding.dehaze.setOnClickListener(this)
//        binding.languagePicker.setOnClickListener(this)
        binding.btnStartQuiz.setOnClickListener(this)
        binding.results.setOnClickListener(this)
        binding.aboutTheTest.setOnClickListener(this)
    }

    private fun loadTestInfoDialog() {
        val dialogTestInfo = Dialog(requireContext(), R.style.NewDialog)
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bind = TestInfoLayoutBinding.inflate(inflater)
        dialogTestInfo.setContentView(bind.root)
        dialogTestInfo.setCancelable(false)

        enableLink(bind.linkToTypesArticle)
        enableLink(bind.whyThisQuizArticle)

        bind.root.setOnClickListener {
            dialogTestInfo.dismiss()
        }

        bind.btnClose.setOnClickListener {
            dialogTestInfo.dismiss()
        }

        dialogTestInfo.show()
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
        }

        bind.btnShare.setOnClickListener {
            viewModel.process(StartAction.ShareResult(bind.resultContainer, requireContext()))
        }

        bind.btnClose.setOnClickListener {
            dialogResults.dismiss()
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

    private fun enableLink(textView: TextView) {
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.removeLinksUnderline()
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.dehaze.id -> (activity as MainActivity).toggleDrawer()

//            binding.languagePicker.id -> {}
            binding.btnStartQuiz.id -> {
                viewModel.process(StartAction.LoadQuiz)
            }

            binding.results.id -> viewModel.process(StartAction.LoadResults)

            binding.aboutTheTest.id -> {
                viewModel.process(StartAction.LoadTestInfo)
            }
        }
    }
}