package com.example.introversion_in_depth.ui.fragments.startFragment

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.databinding.FragmentStartBinding
import com.example.introversion_in_depth.databinding.ResultsLayoutBinding
import com.example.introversion_in_depth.databinding.TestInfoLayoutBinding
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.domain.contracts.BaseFragment
import com.example.introversion_in_depth.domain.datalayer.dataholders.QuizResult
import com.example.introversion_in_depth.ui.MainActivity
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.IntroversionMeter
import com.example.introversion_in_depth.util.LanguageConfig
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
        setTexts()
        enableLink(
            binding.linkToArticle,
            "https://blogs.scientificamerican.com/beautiful-minds/what-kind-of-introvert-are-you/#"
        )
    }

    override fun handleState(viewState: ViewState) {
        when (viewState) {
            is StartState.ResultsLoaded -> {
                loadResultsDialog(viewState)
            }

            StartState.NoResults -> {
                Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),
                    LanguageConfig.getString(R.string.no_results), Snackbar.LENGTH_SHORT).show()
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

    private fun setTexts() {
        binding.ptBrOp.text = LanguageConfig.getString(R.string.portuguese)
        binding.enOp.text = LanguageConfig.getString(R.string.english)
        binding.mainTitle.text = LanguageConfig.getString(R.string.which_kind)
        binding.secondTitle.text = LanguageConfig.getString(R.string.second_title)
        binding.btnStartQuiz.text = LanguageConfig.getString(R.string.start_quiz)
        binding.aboutTheTest.text = LanguageConfig.getString(R.string.about_the_test)
        binding.results.text = LanguageConfig.getString(R.string.results)
        binding.copyrightDisclaimer.text = LanguageConfig.getString(R.string.copyright_disclaimer)
        binding.linkToArticle.text = LanguageConfig.getString(R.string.link_to_article)
    }

    private fun loadTestInfoDialog() {
        val dialogTestInfo = Dialog(requireContext(), R.style.NewDialog)
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bind = TestInfoLayoutBinding.inflate(inflater)
        dialogTestInfo.setContentView(bind.root)
        dialogTestInfo.setCancelable(false)

        bind.txtAboutTheTest.text = LanguageConfig.getString(R.string.about_the_test)

        bind.txtWhyAQuiz.text = LanguageConfig.getString(R.string.why_a_quiz)
        bind.reasoningPart1.text = LanguageConfig.getString(R.string.how_is_it_calculated)
        bind.reasoningPart2.text = LanguageConfig.getString(R.string.how_is_it_calculated2)

        bind.txtTypesOfIntroversion.text = LanguageConfig.getString(R.string.types_of_introversion)
        bind.eachTypeDefinition.text = LanguageConfig.getString(R.string.each_type_definition)

        bind.txtSocial.text = LanguageConfig.getString(R.string.social)
        bind.theSocialIntrovert.text = LanguageConfig.getString(R.string.the_social_introvert)

        bind.txtThinking.text = LanguageConfig.getString(R.string.thinking)
        bind.theThinkingIntrovert.text = LanguageConfig.getString(R.string.the_thinking_introvert)

        bind.txtAnxious.text = LanguageConfig.getString(R.string.anxious)
        bind.theAnxiousIntrovert.text = LanguageConfig.getString(R.string.the_anxious_introvert)

        bind.txtRestrained.text = LanguageConfig.getString(R.string.restrained)
        bind.theRestrainedIntrovert.text = LanguageConfig.getString(R.string.the_restrained_introvert)

        bind.btnClose.text = LanguageConfig.getString(R.string.close)

        enableLink(
            bind.linkToTypesArticle,
            "https://www.refinery29.com/en-gb/types-of-introverts"
        )
        enableLink(
            bind.linkToArticle,
            "https://blogs.scientificamerican.com/beautiful-minds/what-kind-of-introvert-are-you/#"
        )

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

        bind.title.text = LanguageConfig.getString(R.string.results)
        bind.btnShare.text = LanguageConfig.getString(R.string.btn_share)
        bind.btnClose.text = LanguageConfig.getString(R.string.close)

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
        bind.txtSocial.text = LanguageConfig.getString(R.string.social)
        bind.txtThinking.text = LanguageConfig.getString(R.string.thinking)
        bind.txtAnxious.text = LanguageConfig.getString(R.string.anxious)
        bind.txtRestrained.text = LanguageConfig.getString(R.string.restrained)

        bind.socialScore.text  = LanguageConfig.getString(R.string.score, result.socialScore)
        bind.socialLevel.text = IntroversionMeter.checkSocialLevel(result.socialScore)

        bind.thinkingScore.text  = LanguageConfig.getString(R.string.score, result.thinkingScore)
        bind.thinkingLevel.text = IntroversionMeter.checkThinkingLevel(result.thinkingScore)

        bind.anxiousScore.text  = LanguageConfig.getString(R.string.score, result.anxiousScore)
        bind.anxiousLevel.text = IntroversionMeter.checkAnxiousLevel(result.anxiousScore)

        bind.restrainedScore.text  = LanguageConfig.getString(R.string.score, result.restrainedScore)
        bind.restrainedLevel.text = IntroversionMeter.checkRestrainedLevel(result.restrainedScore)
    }

    private fun enableLink(textView: TextView, url: String) {
        val builder = CustomTabsIntent.Builder()
        val customIntent = builder.build()

        textView.setOnClickListener {
            customIntent.launchUrl(requireContext(), Uri.parse(url))
        }

//        textView.movementMethod = LinkMovementMethod.getInstance()
//        textView.removeLinksUnderline()
    }

    private fun setupListeners() {
        binding.language.setOnClickListener(this)
        binding.btnStartQuiz.setOnClickListener(this)
        binding.results.setOnClickListener(this)
        binding.aboutTheTest.setOnClickListener(this)
        binding.enOp.setOnClickListener(this)
        binding.ptBrOp.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.language.id -> {
                binding.languageOpDropdown.isVisible = !binding.languageOpDropdown.isVisible
            }

            binding.enOp.id -> {
                LanguageConfig.setLocale((activity as MainActivity), "en")
                binding.languageOpDropdown.isVisible = !binding.languageOpDropdown.isVisible
                setTexts()
            }

            binding.ptBrOp.id -> {
                LanguageConfig.setLocale((activity as MainActivity), "pt-BR")
                binding.languageOpDropdown.isVisible = !binding.languageOpDropdown.isVisible
                setTexts()
            }

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