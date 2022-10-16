package com.nirwashh.android.mynumbertask.numbers.domain

import com.nirwashh.android.mynumbertask.numbers.presentation.NumberUi

class NumberUiMapper : NumberFact.Mapper<NumberUi> {
    override fun map(id: String, fact: String): NumberUi = NumberUi(id, fact)
}