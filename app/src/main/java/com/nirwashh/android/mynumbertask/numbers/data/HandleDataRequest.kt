package com.nirwashh.android.mynumbertask.numbers.data

import com.nirwashh.android.mynumbertask.numbers.data.cache.NumbersCacheDataSource
import com.nirwashh.android.mynumbertask.numbers.domain.HandleError
import com.nirwashh.android.mynumbertask.numbers.domain.NumberFact

interface HandleDataRequest {

    suspend fun handle(block: suspend () -> NumberData): NumberFact

    class Base(
        private val cacheDataSource: NumbersCacheDataSource,
        private val mapperToDomain: NumberData.Mapper<NumberFact>,
        private val handleError: HandleError<Exception>
    ) : HandleDataRequest {
        override suspend fun handle(block: suspend () -> NumberData) = try {
            val result = block.invoke()
            cacheDataSource.saveNumber(result)
            result.map(mapperToDomain)
        } catch (e: Exception) {
            throw handleError.handle(e)
        }
    }
}