package com.nirwashh.android.mynumbertask

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nirwashh.android.mynumbertask.main.presentation.MainActivity
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    @get: Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    protected fun Int.typeText(value: String) {
        onView(withId(this)).perform(ViewActions.typeText(value))
        closeSoftKeyboard()
    }

    protected fun Int.checkText(value: String) =
        onView(withId(this)).check(matches(withText(value)))

    protected fun Int.click() =
        onView(withId(this)).perform(ViewActions.click())

    protected fun Int.item(position: Int, viewId: Int) =
        onView(RecyclerViewMatcher(this).atPosition(position, viewId))

    protected fun Int.item(position: Int) =
        onView(RecyclerViewMatcher(this).atPosition(position))

    protected fun ViewInteraction.checkText(value: String) =
        check(matches(withText(value)))

    protected fun ViewInteraction.click() =
        perform(ViewActions.click())
}