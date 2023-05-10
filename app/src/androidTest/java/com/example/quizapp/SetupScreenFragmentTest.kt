package com.example.quizapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule
import org.junit.Test

class SetupScreenFragmentTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun setUpScreenLaunch() {
        activityScenarioRule.scenario.onActivity {
                activity -> activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SetupScreenFragment()).commit()
        }

        onView(withId(R.id.setup_screen)).check(matches(isDisplayed()))
        onView(withId(R.id.startButton)).perform(click())
    }
}