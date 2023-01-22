package com.nirwashh.android.mynumbertask.random

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

interface WorkManagerWrapper {

    fun start()

    class Base(context: Context) : WorkManagerWrapper {
        private val workManager = WorkManager.getInstance(context)
        override fun start() {
            val constraint = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()

            val request =
                PeriodicWorkRequestBuilder<PeriodicRandomWorker>(15, TimeUnit.MINUTES)
                    .setConstraints(constraint)
                    .build()
            workManager.enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }

        companion object {
            private const val WORK_NAME = "random periodic work"
        }
    }
}