package com.nirwashh.android.mynumbertask

import androidx.test.espresso.Espresso.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nirwashh.android.mynumbertask.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckItemReplacedTest : BaseTest() {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private val numberOne = "1"
    private val numberTwo = "2"
    private val factOne = "fact about $numberOne"
    private val factTwo = "fact about $numberTwo"
    private val firstPosition = 0
    private val secondPosition = 1

    @Test
    fun test_history(): Unit = NumbersPage().run {
        inputEditText.typeText(numberOne)
        getFactButton.click()
        recyclerView.run {
            item(firstPosition, titleItem).checkText(numberOne)
            item(firstPosition, subTitleItem).checkText(factOne)
        }

        inputEditText.typeText(numberTwo)
        getFactButton.click()
        recyclerView.run {
            item(firstPosition, titleItem).checkText(numberTwo)
            item(firstPosition, subTitleItem).checkText(factTwo)
            item(secondPosition, titleItem).checkText(numberOne)
            item(secondPosition, subTitleItem).checkText(factOne)
        }

        inputEditText.typeText(numberOne)
        getFactButton.click()
        recyclerView.run {
            item(firstPosition, titleItem).checkText(numberOne)
            item(firstPosition, subTitleItem).checkText(factOne)
            item(secondPosition, titleItem).checkText(numberTwo)
            item(secondPosition, subTitleItem).checkText(factTwo)
        }
    }
}