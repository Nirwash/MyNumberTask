package com.nirwashh.android.mynumbertask

import androidx.test.espresso.Espresso.pressBack
import org.junit.Test


class NavigationClassTest : BaseTest() {

    @Test
    fun navigation_test() {
        val numbersPage = NumbersPage()
        val detailsPage = DetailsPage()
        val number = "10"
        val fact = "fact about 10"
        val details = "\n$number\n\n$fact"

        numbersPage.run {
            inputEditText.typeText(number)
            getFactButton.click()
            recyclerView.run {
                item(0, titleItem).checkText(number)
                item(0, subTitleItem).checkText(fact)
                item(0).click()
            }
        }

        detailsPage.run {
            detailsTextView.checkText(details)
            pressBack()
        }

        numbersPage.run {
            recyclerView.run {
                item(0, titleItem).checkText(number)
                item(0, subTitleItem).checkText(fact)
            }
        }
    }
}