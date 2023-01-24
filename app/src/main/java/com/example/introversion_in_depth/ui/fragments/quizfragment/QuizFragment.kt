package com.example.introversion_in_depth.ui.fragments.quizfragment

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.introversion_in_depth.R
import com.example.introversion_in_depth.databinding.DialogContinueQuizBinding
import com.example.introversion_in_depth.databinding.DialogLeaveBinding
import com.example.introversion_in_depth.databinding.FragmentQuizBinding
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.datalayer.entities.entityrelation.QuizWithAnswers
import com.example.introversion_in_depth.ui.MainActivity
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.LanguageConfig
import com.example.introversion_in_depth.util.viewModelsFactory

class QuizFragment : BaseFragment<FragmentQuizBinding>(), View.OnClickListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentQuizBinding
        get() = FragmentQuizBinding::inflate

    private val viewModel by viewModelsFactory {
        val appContext = (activity?.applicationContext as CustomApplication)
        QuizViewModel(this, appContext.appContainer.quizRepository)
    }

    override fun setup() {
        setListeners()
        setBackButtonBehavior()
        setTexts()

        val questionsArray = LanguageConfig.getStringArray(R.array.questions).asList()
        viewModel.process(QuizAction.InitQuiz(requireContext(), questionsArray))
    }

    override fun handleState(viewState: ViewState) {
        when(viewState) {
            QuizState.Finished -> {
                findNavController().currentDestination?.id?.let { destinationId ->
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(destinationId, true)
                        .setEnterAnim(R.anim.fade_in)
                        .build()

                    findNavController().navigate(R.id.action_quiz_to_resultFragment,
                        Bundle().apply { putInt("quizId", viewModel.getQuizId()) },
                        navOptions)
                }
            }

            QuizState.LeavingQuiz -> {
                findNavController().popBackStack()
            }

            QuizState.Loading -> {
                (activity as MainActivity).showLoading()
            }

            QuizState.Idle -> {
                (activity as MainActivity).hideLoading()
            }

            is QuizState.ContinuationPopupLoaded -> {
                loadContinuationDialog(viewState.quizAndAnswers)
            }

            is QuizState.QuestionLoaded -> {
                binding.question.text = viewState.question
                binding.radioGroup.clearCheck()
                viewState.answer?.let {
                    val rb = binding.radioGroup[it.choiceValue-1] as AppCompatRadioButton
                    binding.radioGroup.check(rb.id)
                }

                updateType(viewState.questionCount)

                binding.progress.text = LanguageConfig.getString(R.string.quiz_progress, viewState.questionCount)
                binding.btnBack.isVisible = !viewState.initialQuestion
            }
        }
    }

    private fun setTexts() {
        binding.btnNext.text = LanguageConfig.getString(R.string.btn_next)
        binding.btnBack.text = LanguageConfig.getString(R.string.btn_back)
        binding.rb1.text = LanguageConfig.getString(R.string.ranking_option_1)
        binding.rb2.text = LanguageConfig.getString(R.string.ranking_option_2)
        binding.rb3.text = LanguageConfig.getString(R.string.ranking_option_3)
        binding.rb4.text = LanguageConfig.getString(R.string.ranking_option_4)
        binding.rb5.text = LanguageConfig.getString(R.string.ranking_option_5)
    }

    private fun updateType(count: Int) {
        when {
            count <= 10 -> binding.introversionType.text = LanguageConfig.getString(R.string.social)
            count <= 20 -> binding.introversionType.text = LanguageConfig.getString(R.string.thinking)
            count <= 30 -> binding.introversionType.text = LanguageConfig.getString(R.string.anxious)
            count <= 40 -> binding.introversionType.text = LanguageConfig.getString(R.string.restrained)
        }
    }

    private fun setBackButtonBehavior() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                loadLeaveDialog()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    private fun loadLeaveDialog() {
        val dialogLeave = Dialog(requireContext(), R.style.NewDialog)
        val inflater = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bind = DialogLeaveBinding.inflate(inflater)
        dialogLeave.setContentView(bind.root)
        dialogLeave.setCancelable(false)

        bind.warning.text = LanguageConfig.getString(R.string.warning)
        bind.btnLeave.text = LanguageConfig.getString(R.string.leave)
        bind.btnStay.text = LanguageConfig.getString(R.string.btn_stay)

        bind.btnLeave.setOnClickListener {
            (activity as MainActivity).showLoading()
            viewModel.process(QuizAction.LeaveQuiz)
            dialogLeave.dismiss()
        }
        bind.btnStay.setOnClickListener {
            dialogLeave.dismiss()
        }

        dialogLeave.show()
    }

    private fun loadContinuationDialog(quizAndAnswers: QuizWithAnswers) {
        val dialogContinue = Dialog(requireContext(), R.style.NewDialog)
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bind = DialogContinueQuizBinding.inflate(inflater)
        dialogContinue.setContentView(bind.root)
        dialogContinue.setCancelable(false)

        bind.warning.text = LanguageConfig.getString(R.string.continuation_question)
        bind.btnYes.text = LanguageConfig.getString(R.string.yes)
        bind.btnNo.text = LanguageConfig.getString(R.string.no)

        bind.btnNo.setOnClickListener {
            dialogContinue.dismiss()
            viewModel.process(QuizAction.ClearAndStart)
        }

        bind.btnYes.setOnClickListener {
            dialogContinue.dismiss()
            viewModel.process(QuizAction.RestoreQuiz(quizAndAnswers))
        }
        dialogContinue.show()
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnNext.id -> {
                val rbId = binding.radioGroup.checkedRadioButtonId
                val checkedBtn = binding.radioGroup.findViewById<AppCompatRadioButton>(rbId)

                if(checkedBtn != null) {
                    viewModel.process(
                        QuizAction.MoveToNext(
                            Pair(
                                checkedBtn.text.toString(),
                                binding.radioGroup.indexOfChild(checkedBtn) + 1
                            )
                        )
                    )
                } else {
                    ValueAnimator.ofFloat(0f, 15f, -15f, 0f).apply {
                        duration = 400
                        start()
                    }.addUpdateListener {
                        val animatedValue = it.animatedValue as Float
                        binding.radioGroup.translationY = animatedValue
                    }
                }
            }

            binding.btnBack.id -> {
                viewModel.process(QuizAction.MoveToPrevious)
            }
        }
    }
}