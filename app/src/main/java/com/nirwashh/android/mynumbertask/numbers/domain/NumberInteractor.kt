package com.nirwashh.android.mynumbertask.numbers.domain

interface NumberInteractor {
    suspend fun init(): NumbersResult

    suspend fun factAboutNumber(number: String): NumbersResult

    suspend fun factAboutRandomNumber(): NumbersResult
}