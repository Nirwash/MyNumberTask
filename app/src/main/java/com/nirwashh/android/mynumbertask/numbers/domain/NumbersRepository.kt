package com.nirwashh.android.mynumbertask.numbers.domain

interface NumbersRepository {
    suspend fun allNumbers(): List<NumberFact>

    suspend fun numberFact(number: String): NumberFact

    suspend fun randomNumberFact(): NumberFact
}