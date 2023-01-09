package com.example.introversion_in_depth.ui.fragments.quizfragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateHandler
import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.data.entities.Quiz
import com.example.introversion_in_depth.data.repository.QuizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizViewModel(
    private val view: ViewStateHandler,
    private val quizRepository: QuizRepository
): BaseViewModel<QuizState, QuizAction>() {

    init {
        collect()
    }

    private lateinit var questions: List<String>
    private val answers = ArrayList<Answer>()
    private var quizId = 0
    private var currentIndex = 0

    override fun collect() {
        state.onEach {
            view.handleState(it)
        }.launchIn(viewModelScope)
    }

    override fun process(action: QuizAction) {
        CoroutineScope(Dispatchers.Default).launch {
            when(action) {
                is QuizAction.MoveToNext -> {
                    if(answers.size >= questions.size-1) {
                        setState(QuizState.Finished)
                        return@launch
                    }

                    val answer = Answer(
                        quizId = quizId,
                        choice = action.userChoice.first,
                        choiceValue = action.userChoice.second
                    )

                    if (currentIndex < answers.size) {
                        answer.id = answers[currentIndex].id

                        withContext(Dispatchers.Default)
                        { quizRepository.updateAnswer(answer) }

                        answers[currentIndex] = answer

                        currentIndex++
                        setState(QuizState.QuestionLoaded(
                            questions[currentIndex],
                            if (currentIndex < answers.size) answers[currentIndex] else null,
                            answers.size
                        ))
                        return@launch
                    }

                    val answerId = withContext(Dispatchers.Default)
                    { quizRepository.insertAnswer(answer) }

                    answer.id = answerId.toInt()
                    answers.add(answer)

                    currentIndex++
                    println("$currentIndex")
                    //if(currentIndex < answers.size)
                    setState(QuizState.QuestionLoaded(
                        questions[currentIndex],
                        null,
                        answers.size
                    ))
                }

                QuizAction.MoveToPrevious -> {
                    currentIndex--

                    setState(QuizState.QuestionLoaded(
                        questions[currentIndex],
                        answers[currentIndex],
                        answers.size,
                        currentIndex == 0
                    ))
                }

                is QuizAction.StartNewQuiz -> {
                    questions = action.questions

                    quizId = withContext(Dispatchers.Default)
                    { quizRepository.insertQuiz(Quiz()).toInt() }

                    setState(QuizState.QuestionLoaded(
                        questions[0],
                        null,
                        answers.size
                    ))
                }
            }
        }
    }

    override fun clear() {
    }
}