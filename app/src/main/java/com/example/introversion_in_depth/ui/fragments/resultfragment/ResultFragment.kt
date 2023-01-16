package com.example.introversion_in_depth.ui.fragments.resultfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.ui.fragments.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentResultBinding
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.ui.MainActivity
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.IntroversionMeter
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
            viewModel.process(ResultAction.ReckonResult(quizId))
        }
    }

    override fun handleState(viewState: ViewState) {
        when(viewState) {
            is ResultState.ResultReckoned -> {
                IntroversionMeter.detectStrongerType(arrayOf(
                    viewState.data.socialScore,
                    viewState.data.thinkingScore,
                    viewState.data.anxiousScore,
                    viewState.data.restrainedScore)).let { strongerType ->

                    when(strongerType) {
                        0 -> binding.result.text = resources.getString(R.string.r_social)
                        1 -> binding.result.text = resources.getString(R.string.thinking)
                        2 -> binding.result.text = resources.getString(R.string.anxious)
                        3 -> binding.result.text = resources.getString(R.string.restrained)
                        else -> {
                            binding.mostly.visibility = View.GONE
                            binding.result.visibility = View.GONE
                        }
                    }
                }

                binding.socialScore.text  = resources.getString(R.string.score, viewState.data.socialScore)
                binding.socialLevel.text = IntroversionMeter.checkSocialLevel(resources, viewState.data.socialScore)

                binding.thinkingScore.text  = resources.getString(R.string.score, viewState.data.thinkingScore)
                binding.thinkingLevel.text = IntroversionMeter.checkThinkingLevel(resources, viewState.data.thinkingScore)

                binding.anxiousScore.text  = resources.getString(R.string.score, viewState.data.anxiousScore)
                binding.anxiousLevel.text = IntroversionMeter.checkAnxiousLevel(resources, viewState.data.anxiousScore)

                binding.restrainedScore.text  = resources.getString(R.string.score, viewState.data.restrainedScore)
                binding.restrainedLevel.text = IntroversionMeter.checkRestrainedLevel(resources, viewState.data.restrainedScore)

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