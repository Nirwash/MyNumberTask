package com.nirwashh.android.mynumbertask.main.sl

import androidx.lifecycle.ViewModel

interface Module<T : ViewModel> {

    fun viewModel(): T
}