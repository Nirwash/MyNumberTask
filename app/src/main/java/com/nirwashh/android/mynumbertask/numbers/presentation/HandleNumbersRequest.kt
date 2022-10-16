package com.nirwashh.android.mynumbertask.numbers.presentation

import com.nirwashh.android.mynumbertask.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandleNumbersRequest {

    fun handle(
        coroutineScore: CoroutineScope,
        block: suspend ()-> NumbersResult
    )

    class Base(
        private val dispatchers: DispatchersList,
        private val communications: NumbersCommunications,
        private val numberResultMapper: NumbersResult.Mapper<Unit>
    ) : HandleNumbersRequest{
        override fun handle(coroutineScore: CoroutineScope, block: suspend () -> NumbersResult) {
            communications.showProgress(true)
            coroutineScore.launch(dispatchers.io()) {
                val result = block.invoke()
                communications.showProgress(false)
                result.map(numberResultMapper)
            }
        }
    }
}