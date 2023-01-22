package com.nirwashh.android.mynumbertask.numbers.domain

import com.nirwashh.android.mynumbertask.details.data.NumberDetails

interface NumbersInteractor {

    fun saveDetails(details: String)

    suspend fun init(): NumbersResult

    suspend fun factAboutNumber(number: String): NumbersResult

    suspend fun factAboutRandomNumber(): NumbersResult

    class Base(
        private val repository: NumbersRepository,
        private val handleRequest: HandleRequest,
        private val numberDetails: NumberDetails.Save
    ) : NumbersInteractor {
        override fun saveDetails(details: String) = numberDetails.save(details)

        override suspend fun init() = NumbersResult.Success(repository.allNumbers())

        override suspend fun factAboutNumber(number: String) = handleRequest.handle {
            repository.numberFact(number)
        }

        override suspend fun factAboutRandomNumber() = handleRequest.handle {
            repository.randomNumberFact()
        }
    }
}
