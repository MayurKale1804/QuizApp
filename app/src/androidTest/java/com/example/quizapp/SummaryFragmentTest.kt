package com.example.quizapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule
import org.junit.Test

class SummaryFragmentTest {
    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun summary_launch_restart_click_check() {
        activityScenarioRule.scenario.onActivity {
                activity -> activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SummaryFragment()).commit()
        }

        onView(withId(R.id.summary_screen)).check(matches(isDisplayed()))
        onView(withId(R.id.restartButton)).perform(click())
    }
}