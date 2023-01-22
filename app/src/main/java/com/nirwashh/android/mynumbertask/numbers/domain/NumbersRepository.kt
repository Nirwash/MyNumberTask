package com.nirwashh.android.mynumbertask.numbers.domain

interface NumbersRepository : RandomNumbersRepository {
    suspend fun allNumbers(): List<NumberFact>

    suspend fun numberFact(number: String): NumberFact
}

interface RandomNumbersRepository {
    suspend fun randomNumberFact(): NumberFact
}