package com.nirwashh.android.mynumbertask.numbers.data.cloud

import retrofit2.Response

class MockNumbersService : NumbersService {
    override suspend fun fact(id: String) = "fact about $id"

    override suspend fun random(): Response<String> {
        TODO("Not yet implemented")
    }
}