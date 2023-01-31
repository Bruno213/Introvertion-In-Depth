package com.example.introversion_in_depth

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.introversion_in_depth.util.saveImage
import org.junit.Assert.*
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var bitmap: Bitmap




    @Test
    fun addition_isCorrect() {
        val closable = MockitoAnnotations.openMocks(this)
        assertEquals(true, isValidUri())

        closable.close()
    }


    fun isValidUri(): Boolean {
        val temporaryFolder = TemporaryFolder()
        temporaryFolder.create()

        val file = File(temporaryFolder.root, "images")
        mockContext = mock(Context::class.java)


        return when(saveImage(file, mockContext, bitmap)) {
            is Uri -> true

            else -> false
        }
    }
}