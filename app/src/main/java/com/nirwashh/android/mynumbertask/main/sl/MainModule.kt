package com.nirwashh.android.mynumbertask.main.sl

import com.nirwashh.android.mynumbertask.main.presentation.MainViewModel

class MainModule(private val core: Core) : Module<MainViewModel> {
    override fun viewModel() = MainViewModel(
        core.provideWorkManagerWrapper(),
        core.provideNavigation()
    )
}