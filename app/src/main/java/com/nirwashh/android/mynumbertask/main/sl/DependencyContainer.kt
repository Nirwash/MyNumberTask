package com.nirwashh.android.mynumbertask.main.sl

import androidx.lifecycle.ViewModel
import com.nirwashh.android.mynumbertask.details.presentation.NumberDetailsViewModel
import com.nirwashh.android.mynumbertask.details.sl.NumberDetailsModule
import com.nirwashh.android.mynumbertask.main.presentation.MainViewModel
import com.nirwashh.android.mynumbertask.numbers.presentation.NumbersViewModel
import com.nirwashh.android.mynumbertask.numbers.sl.NumbersModule

interface DependencyContainer {

    fun <T : ViewModel> module(clasz: Class<T>): Module<*>

    class Error : DependencyContainer {
        override fun <T : ViewModel> module(clasz: Class<T>): Module<*> {
            throw IllegalStateException("not found module for $clasz")
        }
    }

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error()
    ) : DependencyContainer {
        override fun <T : ViewModel> module(clasz: Class<T>): Module<*> =
            when (clasz) {
                MainViewModel::class.java -> MainModule(core)
                NumbersViewModel.Base::class.java -> NumbersModule(core)
                NumberDetailsViewModel::class.java -> NumberDetailsModule(core)
                else -> dependencyContainer.module(clasz)
            }
    }
}
