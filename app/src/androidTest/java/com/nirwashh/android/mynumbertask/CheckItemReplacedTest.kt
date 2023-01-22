package com.nirwashh.android.mynumbertask

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nirwashh.android.mynumbertask.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckItemReplacedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_history() {
        onView(ViewMatchers.withId(R.id.editText)).perform(ViewActions.typeText("1"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.getFactButton)).perform(ViewActions.click())

        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.titleTextView))
            .check(matches(withText("1")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.subTitleTextView))
            .check(matches(withText("fact about 1")))

        onView(ViewMatchers.withId(R.id.editText)).perform(ViewActions.typeText("2"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.getFactButton)).perform(ViewActions.click())
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.titleTextView))
            .check(matches(withText("2")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.subTitleTextView))
            .check(matches(withText("fact about 2")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.titleTextView))
            .check(matches(withText("1")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.subTitleTextView))
            .check(matches(withText("fact about 1")))

        onView(ViewMatchers.withId(R.id.editText)).perform(ViewActions.typeText("1"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.getFactButton)).perform(ViewActions.click())

        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.titleTextView))
            .check(matches(withText("1")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.subTitleTextView))
            .check(matches(withText("fact about 1")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.titleTextView))
            .check(matches(withText("2")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.subTitleTextView))
            .check(matches(withText("fact about 2")))

    }
}