package com.nirwashh.android.mynumbertask.numbers.data

interface NumbersCloudDataSource : FetchNumber {

    suspend fun randomNumber(): NumberData
}