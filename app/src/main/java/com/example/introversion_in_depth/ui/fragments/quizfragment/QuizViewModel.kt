package com.example.introversion_in_depth.ui.fragments.quizfragment

import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.ui.fragments.base.BaseViewModel
import com.example.introversion_in_depth.ui.fragments.base.ViewStateHandler
import com.example.introversion_in_depth.data.entities.Answer
import com.example.introversion_in_depth.data.entities.Quiz
import com.example.introversion_in_depth.data.repository.QuizRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class QuizViewModel(
    private val view: ViewStateHandler,
    private val quizRepository: QuizRepository
): BaseViewModel<QuizState, QuizAction>() {

    init {
        collect()
    }

    private lateinit var questions: List<String>
    private val answers = ArrayList<Answer>()
    private var currentIndex = 0
    private var quizId = 0

    override fun collect() {
        state.onEach {
            view.handleState(it)
        }.launchIn(viewModelScope)
    }

    override fun process(action: QuizAction) {
        CoroutineScope(Dispatchers.Default).launch {
            when(action) {
                is QuizAction.MoveToNext -> {
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
                            currentIndex+1
                        ))
                        return@launch
                    }

                    val answerId = withContext(Dispatchers.Default)
                    { quizRepository.insertAnswer(answer) }

                    answer.id = answerId.toInt()
                    answers.add(answer)

                    currentIndex++
                    println("$currentIndex")

                    if(answers.size >= questions.size) {
                        setState(QuizState.Loading)
                        delay(800)
                        setState(QuizState.Finished)
                        return@launch
                    }

                    delay(200)
                    setState(QuizState.QuestionLoaded(
                        questions[currentIndex],
                        null,
                        currentIndex+1
                    ))
                }

                QuizAction.MoveToPrevious -> {
                    currentIndex--

                    setState(QuizState.QuestionLoaded(
                        questions[currentIndex],
                        answers[currentIndex],
                        currentIndex+1,
                        currentIndex == 0
                    ))
                }

                is QuizAction.StartNewQuiz -> {
                    questions = action.questions

                    quizId = withContext(Dispatchers.Default) {
                        val count = quizRepository.getQuizCount() + 1
                        quizRepository.insertQuiz(Quiz(code = count.toString())).toInt()
                    }

                    setState(QuizState.QuestionLoaded(
                        questions[0],
                        null,
                        1,
                        true
                    ))
                    delay(300)
                    setState(QuizState.Idle)
                }

                QuizAction.LeaveQuiz -> {
                    quizRepository.deleteAnswers(quizId)
                    val quiz = withContext(Dispatchers.Default)
                    { quizRepository.getQuiz(quizId) }
                    quizRepository.deleteQuiz(quiz)

                    setState(QuizState.LeavingQuiz)
                }
            }
        }
    }

    fun getQuizId(): Int {
        return quizId
    }

    override fun clear() {
    }
}