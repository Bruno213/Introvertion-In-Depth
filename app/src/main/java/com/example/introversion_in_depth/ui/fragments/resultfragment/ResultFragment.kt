package com.example.introversion_in_depth.ui.fragments.resultfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentResultBinding
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.ui.MainActivity
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.IntroversionMeter
import com.example.introversion_in_depth.util.LanguageConfig
import com.example.introversion_in_depth.util.shareImageUri
import com.example.introversion_in_depth.util.viewModelsFactory

class ResultFragment : BaseFragment<FragmentResultBinding>(), View.OnClickListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentResultBinding
        get() = FragmentResultBinding::inflate

    private val viewModel by viewModelsFactory {
        val appContext = (activity?.applicationContext as CustomApplication)
        ResultViewModel(this, appContext.appContainer.quizRepository, appContext.appContainer.file)
    }

    override fun setup() {
        setListeners()
        arguments?.let {
            val quizId = it.getInt("quizId")
            setTexts()
            viewModel.process(ResultAction.ReckonResult(quizId))
        }
    }

    override fun handleState(viewState: ViewState) {
        when(viewState) {
            is ResultState.ResultReckoned -> {
                binding.socialScore.text  = LanguageConfig.getString(R.string.score, viewState.data.socialScore)
                binding.socialLevel.text = IntroversionMeter.checkSocialLevel(viewState.data.socialScore)

                binding.thinkingScore.text  = LanguageConfig.getString(R.string.score, viewState.data.thinkingScore)
                binding.thinkingLevel.text = IntroversionMeter.checkThinkingLevel(viewState.data.thinkingScore)

                binding.anxiousScore.text  = LanguageConfig.getString(R.string.score, viewState.data.anxiousScore)
                binding.anxiousLevel.text = IntroversionMeter.checkAnxiousLevel(viewState.data.anxiousScore)

                binding.restrainedScore.text  = LanguageConfig.getString(R.string.score, viewState.data.restrainedScore)
                binding.restrainedLevel.text = IntroversionMeter.checkRestrainedLevel(viewState.data.restrainedScore)

                viewModel.process(ResultAction.SetToIdle)
            }

            is ResultState.SharingResult -> {
                viewState.data?.let {
                    shareImageUri(requireContext(), it)
                }
            }

            ResultState.LeavingResult -> {
                findNavController().popBackStack()
            }

            ResultState.Idle -> {
                (activity as MainActivity).hideLoading()
            }
        }
    }

    private fun setTexts() {
        binding.btnShare.text = LanguageConfig.getString(R.string.btn_share)

        binding.txtSocial.text = LanguageConfig.getString(R.string.social)
        binding.txtAnxious.text = LanguageConfig.getString(R.string.anxious)
        binding.txtThinking.text = LanguageConfig.getString(R.string.thinking)
        binding.txtRestrained.text = LanguageConfig.getString(R.string.restrained)
    }

    private fun setListeners() {
        binding.btnShare.setOnClickListener(this)
        binding.btnClose.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnShare.id -> {
                viewModel.process(ResultAction.ShareResult(binding.resultContainer, requireContext()))
            }

            binding.btnClose.id -> {
                viewModel.process(ResultAction.LeaveResult)
            }
        }
    }
}