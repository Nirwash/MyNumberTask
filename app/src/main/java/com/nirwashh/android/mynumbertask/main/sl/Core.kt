package com.nirwashh.android.mynumbertask.main.sl

import android.content.Context
import com.nirwashh.android.mynumbertask.numbers.data.cache.CacheModule
import com.nirwashh.android.mynumbertask.numbers.data.cloud.CloudModule
import com.nirwashh.android.mynumbertask.numbers.presentation.DispatchersList
import com.nirwashh.android.mynumbertask.numbers.presentation.ManageResources

interface Core : CloudModule, CacheModule, ManageResources {


    fun provideDispatchers(): DispatchersList

    class Base(
        context: Context,
        private val isRelease: Boolean
    ) : Core {
        private val manageResources = ManageResources.Base(context)
        private val dispatchersList by lazy {
            DispatchersList.Base()
        }
        private val cloudModule by lazy {
            if (isRelease)
                CloudModule.Base()
            else
                CloudModule.Mock()
        }

        private val cacheModule by lazy {
            if (isRelease)
                CacheModule.Base(context)
            else
                CacheModule.Mock(context)
        }

        override fun <T> service(clasz: Class<T>): T = cloudModule.service(clasz)

        override fun provideDatabase() = cacheModule.provideDatabase()

        override fun string(id: Int): String = manageResources.string(id)

        override fun provideDispatchers() = dispatchersList

    }
}