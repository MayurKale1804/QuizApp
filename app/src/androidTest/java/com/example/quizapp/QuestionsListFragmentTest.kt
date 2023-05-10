package com.example.quizapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuestionsListFragmentTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun questionsList_scroll_and_submitClick() {
        activityScenarioRule.scenario.onActivity { activity ->
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, QuestionsListFragment()).commit()
        }

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9))
        onView(withId(R.id.questionsListSubmitButton)).perform(click())
        onView(withText("Are you sure you want to Submit the test ?")).check(matches(isDisplayed()))
        onView(withText("Yes")).perform(click())
    }
}