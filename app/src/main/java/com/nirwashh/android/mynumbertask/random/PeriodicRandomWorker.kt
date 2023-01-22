package com.nirwashh.android.mynumbertask.random

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nirwashh.android.mynumbertask.numbers.domain.RandomNumbersRepository

class PeriodicRandomWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val repository =
                (applicationContext as ProvidePeriodicRepository).providePeriodicRepository()
            repository.randomNumberFact()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

interface ProvidePeriodicRepository {
    fun providePeriodicRepository(): RandomNumbersRepository
}