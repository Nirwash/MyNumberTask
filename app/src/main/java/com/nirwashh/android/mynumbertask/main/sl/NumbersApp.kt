package com.nirwashh.android.mynumbertask.main.sl

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.nirwashh.android.mynumbertask.BuildConfig
import com.nirwashh.android.mynumbertask.random.ProvidePeriodicRepository


class NumbersApp : Application(), ProvideViewModel, ProvidePeriodicRepository {
    private lateinit var viewModelsFactory: ViewModelsFactory
    private lateinit var dependencyContainer: DependencyContainer.Base

    override fun onCreate() {
        super.onCreate()
        val provideInstances = if (!BuildConfig.DEBUG)
            ProvideInstances.Mock(this)
        else
            ProvideInstances.Release(this)
        dependencyContainer = DependencyContainer.Base(Core.Base(this, provideInstances))
        viewModelsFactory = ViewModelsFactory(dependencyContainer)
    }

    override fun <T : ViewModel> provideViewModel(clasz: Class<T>, owner: ViewModelStoreOwner): T =
        ViewModelProvider(owner, viewModelsFactory)[clasz]

    override fun providePeriodicRepository() = dependencyContainer.provideNumbersRepository()
}