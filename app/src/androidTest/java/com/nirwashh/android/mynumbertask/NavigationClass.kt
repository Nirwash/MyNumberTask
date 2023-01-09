package com.nirwashh.android.mynumbertask

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nirwashh.android.mynumbertask.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavigationClass {

    @get: Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun navigation_test() {
        onView(withId(R.id.editText)).perform(typeText("10"))
        onView(withId(R.id.getFactButton)).perform(click())

        onView(withId(R.id.titleTextView)).check(matches(withText("10")))
        onView(withId(R.id.subTitleTextView)).check(matches(withText("fact about 10")))

        onView(withId(R.id.subTitleTextView)).perform(click())
        onView(withId(R.id.detailsTextView)).check(
            matches(
                withText(
                    "\n10\n\nfact about 10"
                )
            )
        )
    }
}