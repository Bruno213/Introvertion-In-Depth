package com.example.introversion_in_depth.ui.fragments.quizfragment

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
import com.example.introversion_in_depth.ui.fragments.base.BaseFragment
import com.example.introversion_in_depth.databinding.DialogLeaveBinding
import com.example.introversion_in_depth.databinding.FragmentQuizBinding
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.ui.MainActivity
import com.example.introversion_in_depth.ui.ViewState
import com.example.introversion_in_depth.util.viewModelsFactory
import com.google.android.material.snackbar.Snackbar

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

        val questionsArray = resources.getStringArray(R.array.questions).asList()
        viewModel.process(QuizAction.StartNewQuiz(questionsArray))
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
                (activity as MainActivity).showLoading()
                findNavController().popBackStack()
            }

            QuizState.Loading -> {
                (activity as MainActivity).showLoading()
            }

            QuizState.Idle -> {
                (activity as MainActivity).hideLoading()
            }

            is QuizState.QuestionLoaded -> {
                binding.question.text = viewState.question
                binding.radioGroup.clearCheck()
                viewState.answer?.let {
                    val rb = binding.radioGroup[it.choiceValue-1] as AppCompatRadioButton
                    binding.radioGroup.check(rb.id)
                }

                updateType(viewState.questionCount)

                binding.progress.text = resources.getString(R.string.quiz_progress, viewState.questionCount)
                binding.btnBack.isVisible = !viewState.initialQuestion
            }
        }
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
    }

    private fun updateType(count: Int) {
        when {
            count <= 10 -> binding.introversionType.text = resources.getString(R.string.social)
            count <= 20 -> binding.introversionType.text = resources.getString(R.string.thinking)
            count <= 30 -> binding.introversionType.text = resources.getString(R.string.anxious)
            count <= 40 -> binding.introversionType.text = resources.getString(R.string.restrained)
        }
    }

    private fun setBackButtonBehavior() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dialogLeave = Dialog(requireContext(), R.style.NewDialog)
                val inflater = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val bind = DialogLeaveBinding.inflate(inflater)
                dialogLeave.setContentView(bind.root)
                bind.btnLeave.setOnClickListener {
                    viewModel.process(QuizAction.LeaveQuiz)
                    dialogLeave.dismiss()
                }
                bind.btnStay.setOnClickListener {
                    dialogLeave.dismiss()
                }

                dialogLeave.show()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
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
                    Snackbar.make((activity as MainActivity).findViewById(android.R.id.content),
                        R.string.must_select, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

            binding.btnBack.id -> {
                viewModel.process(QuizAction.MoveToPrevious)
            }
        }
    }
}