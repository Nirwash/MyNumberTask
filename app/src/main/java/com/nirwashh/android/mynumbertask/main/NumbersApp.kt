package com.nirwashh.android.mynumbertask.main

import android.app.Application
import com.nirwashh.android.mynumbertask.BuildConfig
import com.nirwashh.android.mynumbertask.numbers.data.CloudModule


class NumbersApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val cloudModule = if (BuildConfig.DEBUG)
            CloudModule.Debug()
        else
            CloudModule.Release()
    }
}