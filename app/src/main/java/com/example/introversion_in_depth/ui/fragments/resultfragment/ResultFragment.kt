package com.example.introversion_in_depth.ui.fragments.resultfragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentResultBinding
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.viewModelsFactory

class ResultFragment : BaseFragment<FragmentResultBinding>(), View.OnClickListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentResultBinding
        get() = FragmentResultBinding::inflate

    private val viewModel by viewModelsFactory {
        val appContext = (activity?.applicationContext as CustomApplication)
        ResultViewModel(this, appContext.appContainer.quizRepository)
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
                viewModel.detectStrongerType(arrayOf(
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
                binding.socialLevel.text = checkSocialLevel(viewState.data.socialScore)

                binding.thinkingScore.text  = resources.getString(R.string.score, viewState.data.thinkingScore)
                binding.thinkingLevel.text = checkThinkingLevel(viewState.data.thinkingScore)

                binding.anxiousScore.text  = resources.getString(R.string.score, viewState.data.anxiousScore)
                binding.anxiousLevel.text = checkAnxiousLevel(viewState.data.anxiousScore)

                binding.restrainedScore.text  = resources.getString(R.string.score, viewState.data.restrainedScore)
                binding.restrainedLevel.text = checkRestrainedLevel(viewState.data.restrainedScore)
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
                val textEntry = "Just a POT (Plain Old Text)"
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_TEXT,textEntry)
                startActivity(Intent.createChooser(sharingIntent, "Share File"))
            }

            binding.btnClose.id -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun checkSocialLevel(score: Int): String {
        return resources.getString(
            when {
                score < 24 -> R.string.low
                score <= 36 -> R.string.average
                else -> R.string.low
            }
        )
    }

    private fun checkThinkingLevel(score: Int): String {
        return resources.getString(
            when {
                score < 28 -> R.string.low
                score <= 40 -> R.string.average
                else -> R.string.high
            }
        )
    }

    private fun checkAnxiousLevel(score: Int): String {
        return resources.getString(
            when {
                score < 23 -> R.string.low
                score <= 37 -> R.string.average
                else -> R.string.high
            }
        )
    }

    private fun checkRestrainedLevel(score: Int): String {
        return resources.getString(
            when {
                score < 25 -> R.string.low
                score <= 37 -> R.string.average
                else -> R.string.high
            }
        )
    }
}