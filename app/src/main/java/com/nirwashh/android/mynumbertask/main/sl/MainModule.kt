package com.nirwashh.android.mynumbertask.main.sl

import com.nirwashh.android.mynumbertask.main.presentation.MainViewModel

class MainModule(private val provideNavigation: ProvideNavigation) : Module<MainViewModel> {
    override fun viewModel() = MainViewModel(provideNavigation.provideNavigation())
}