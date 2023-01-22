package com.nirwashh.android.mynumbertask.main.sl

import android.content.Context
import com.nirwashh.android.mynumbertask.details.data.NumberDetails
import com.nirwashh.android.mynumbertask.main.presentation.NavigationCommunication
import com.nirwashh.android.mynumbertask.numbers.data.cache.CacheModule
import com.nirwashh.android.mynumbertask.numbers.data.cloud.CloudModule
import com.nirwashh.android.mynumbertask.numbers.presentation.DispatchersList
import com.nirwashh.android.mynumbertask.numbers.presentation.ManageResources

interface Core : CloudModule, CacheModule, ManageResources, ProvideNavigation,
    ProvideNumberDetails {

    fun provideDispatchers(): DispatchersList

    class Base(
        context: Context,
        private val provideInstances: ProvideInstances
    ) : Core {
        private val manageResources = ManageResources.Base(context)
        private val dispatchersList by lazy {
            DispatchersList.Base()
        }
        private val cloudModule by lazy {
            provideInstances.provideCloudModule()
        }

        private val cacheModule by lazy {
            provideInstances.provideCacheModule()
        }

        private val navigationCommunication = NavigationCommunication.Base()

        private val numberDetails = NumberDetails.Base()

        override fun <T> service(clasz: Class<T>): T = cloudModule.service(clasz)

        override fun provideDatabase() = cacheModule.provideDatabase()

        override fun string(id: Int): String = manageResources.string(id)

        override fun provideNavigation() = navigationCommunication

        override fun provideNumberDetails() = numberDetails

        override fun provideDispatchers() = dispatchersList
    }
}

interface ProvideNavigation {
    fun provideNavigation(): NavigationCommunication.Mutable
}

interface ProvideNumberDetails {
    fun provideNumberDetails(): NumberDetails.Mutable
}