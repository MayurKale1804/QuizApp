package com.example.quizapp


import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class QuestionDetailsFragmentTest {

    @get:Rule
    var mTestActivity: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    private var testActivity: TestActivity? = null

    @Before
    fun setUp() {
        testActivity = mTestActivity.activity

    }

    @After
    fun tearDown() {
        testActivity = null
    }

    @Test
    fun testQuestionDetailsNextFragment() {
        val container = testActivity?.findViewById<LinearLayout>(R.id.testContainer)
        val fragment = QuestionDetailsFragment()
        testActivity?.supportFragmentManager?.beginTransaction()?.add(container!!.id, fragment)
            ?.commitAllowingStateLoss()
        getInstrumentation().waitForIdleSync();

        onView(withId(R.id.nextText)).perform(click())
    }
}

