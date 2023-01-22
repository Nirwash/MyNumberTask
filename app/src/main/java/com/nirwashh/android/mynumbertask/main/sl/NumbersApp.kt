package com.nirwashh.android.mynumbertask.main.sl

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.nirwashh.android.mynumbertask.BuildConfig


class NumbersApp : Application(), ProvideViewModel {
    private lateinit var viewModelsFactory: ViewModelsFactory

    override fun onCreate() {
        super.onCreate()
        val provideInstances = if (!BuildConfig.DEBUG)
            ProvideInstances.Mock(this)
        else
            ProvideInstances.Release(this)
        viewModelsFactory =
            ViewModelsFactory(
                DependencyContainer.Base(
                    Core.Base(
                        this,
                        provideInstances
                    )
                )
            )
    }

    override fun <T : ViewModel> provideViewModel(clasz: Class<T>, owner: ViewModelStoreOwner): T =
        ViewModelProvider(owner, viewModelsFactory)[clasz]
}