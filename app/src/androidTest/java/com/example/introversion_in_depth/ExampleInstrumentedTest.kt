package com.example.introversion_in_depth

import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.introversion_in_depth.di.CustomApplication
import com.example.introversion_in_depth.ui.MainActivity
import com.example.introversion_in_depth.util.saveImage
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get : Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.introversion_in_depth", appContext.packageName)
//
//        val file = (appContext .applicationContext as CustomApplication).appContainer.file
//        val bitmap = ContextCompat.getDrawable(appContext, R.drawable.ic_arrow_drop_down)?.toBitmap()
//
//        if(bitmap != null) {
//            val savedImage = saveImage(file, appContext, bitmap)
//            assert(savedImage is Uri)
//        } else {
//            println("\n Failed to generate bitmap. \n")
//        }


        onView(withId(R.id.btn_start_quiz)).perform(click())
    }
}