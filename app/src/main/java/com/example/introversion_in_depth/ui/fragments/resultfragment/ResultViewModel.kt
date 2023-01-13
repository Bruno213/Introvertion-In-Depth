package com.example.introversion_in_depth.ui.fragments.resultfragment

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import com.example.introversion_in_depth.base.BaseViewModel
import com.example.introversion_in_depth.base.ViewStateHandler
import com.example.introversion_in_depth.data.dataholders.QuizResult
import com.example.introversion_in_depth.data.repository.QuizRepository
import com.example.introversion_in_depth.util.IntroversionMeter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ResultViewModel(
    private val view:ViewStateHandler,
    private val quizRepository: QuizRepository,
    private val file: File
    ): BaseViewModel<ResultState, ResultAction>() {

    init {
        collect()
    }

    override fun collect() {
        state.onEach {
            view.handleState(it)
        }.launchIn(viewModelScope)
    }

    override fun process(action: ResultAction) {
        CoroutineScope(Dispatchers.Default).launch {
            when(action) {
                is ResultAction.ReckonResult -> {
                    val quizWithAnswers = withContext(Dispatchers.Default)
                    { quizRepository.getQuizWithAnswers(action.quizId) }

                    val quizResult = QuizResult(
                        socialScore = IntroversionMeter.computeSocialScore(quizWithAnswers.answers),
                        thinkingScore = IntroversionMeter.computeThinkingScore(quizWithAnswers.answers),
                        anxiousScore = IntroversionMeter.computeAnxiousScore(quizWithAnswers.answers),
                        restrainedScore = IntroversionMeter.computeRestrainedScore(quizWithAnswers.answers)
                    )

                    val quiz = withContext(Dispatchers.Default)
                    { quizRepository.getQuiz(action.quizId) }

                    quizRepository.updateQuiz(quiz.copy(result = quizResult))
                    setState(ResultState.ResultReckoned(quizResult))
                }

                ResultAction.SetToIdle -> {
                    delay(200)
                    setState(ResultState.Idle)
                }

                ResultAction.LeaveResult -> {
                    delay(200)
                    setState(ResultState.LeavingResult)
                }

                is ResultAction.ShareResult -> {

                   setState(
                       ResultState.SharingResult(
                           saveImage(action.context, screenShot(action.view))
                       )
                   )
                }
            }
        }
    }

    private fun saveImage(context: Context, image: Bitmap): Uri? {
        val uri: Uri? = try {
            file.mkdirs()
            val fileImage = File(file, "shared_image.png")
            val stream = FileOutputStream(fileImage)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()

            FileProvider.getUriForFile(context, "com.com.introversion_in_depth.fileprovider", fileImage)
        } catch (e: IOException) {
            Log.d(TAG, "IOException while trying to write file for sharing: " + e.message)
            null
        }
        return uri
    }


    private fun screenShot(view: View): Bitmap {
        val bitmap: Bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    override fun clear() {
    }
}