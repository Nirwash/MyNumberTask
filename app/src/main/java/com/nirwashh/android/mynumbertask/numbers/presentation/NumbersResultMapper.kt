package com.nirwashh.android.mynumbertask.numbers.presentation

import com.nirwashh.android.mynumbertask.numbers.domain.NumberFact
import com.nirwashh.android.mynumbertask.numbers.domain.NumbersResult

class NumbersResultMapper(
    private val communications: NumbersCommunications,
    private val mapper: NumberFact.Mapper<NumberUi>
) : NumbersResult.Mapper<Unit> {

    override fun map(list: List<NumberFact>, errorMessage: String) =
        communications.showState(
            if (errorMessage.isEmpty()) {
                if (list.isNotEmpty())
                    communications.showList(list.map { it.map(mapper) })
                UiState.Success()
            } else
                UiState.Error(errorMessage)
        )
}
