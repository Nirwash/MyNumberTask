package com.nirwashh.android.mynumbertask

import org.junit.Test

class RandomFactTest : BaseTest() {
    private val numberOne = "1"
    private val numberTwo = "2"
    private val factOne = "fact about $numberOne"
    private val factTwo = "fact about $numberTwo"
    private val firstPosition = 0
    private val secondPosition = 1

    @Test
    fun test_random(): Unit = NumbersPage().run {
        getRandomFactButton.click()
        recyclerView.run {
            item(firstPosition, titleItem).checkText(numberOne)
            item(firstPosition, subTitleItem).checkText(factOne)
        }

        getRandomFactButton.click()
        recyclerView.run {
            item(firstPosition, titleItem).checkText(numberTwo)
            item(firstPosition, subTitleItem).checkText(factTwo)
            item(secondPosition, titleItem).checkText(numberOne)
            item(secondPosition, subTitleItem).checkText(factOne)
        }
    }
}