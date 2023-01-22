package com.nirwashh.android.mynumbertask.main.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.nirwashh.android.mynumbertask.main.sl.ProvideViewModel

abstract class BaseFragment<T : ViewModel> : Fragment() {
    protected lateinit var viewModel: T
    protected abstract val viewModelClass: Class<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ProvideViewModel).provideViewModel(
            viewModelClass, this
        )
    }
}