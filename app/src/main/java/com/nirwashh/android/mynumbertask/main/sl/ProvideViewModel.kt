package com.nirwashh.android.mynumbertask.main.sl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner

interface ProvideViewModel {

    fun <T : ViewModel> provideViewModel(clasz: Class<T>, owner: ViewModelStoreOwner): T
}