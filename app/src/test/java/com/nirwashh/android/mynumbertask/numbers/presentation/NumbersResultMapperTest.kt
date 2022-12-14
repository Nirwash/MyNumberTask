package com.nirwashh.android.mynumbertask.numbers.presentation

import com.nirwashh.android.mynumbertask.numbers.domain.NumberFact
import com.nirwashh.android.mynumbertask.numbers.domain.NumberUiMapper
import org.junit.Assert.*
import org.junit.Test

class NumbersResultMapperTest : BaseTest() {

    @Test
    fun `test error`() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.map(emptyList(), "not empty message")

        assertEquals(UiState.ShowError("not empty message"), communications.stateCalledList[0])
    }


    @Test
    fun `test success empty list`() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.map(emptyList(), "")

        assertEquals(0, communications.timesShowList)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)
    }

    @Test
    fun `test success no empty list`() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.map(listOf(NumberFact("5", "fact about 5")), "")

        assertEquals(true, communications.stateCalledList[0] is UiState.Success)
        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("5", "fact about 5"), communications.numbersList[0])
    }

}