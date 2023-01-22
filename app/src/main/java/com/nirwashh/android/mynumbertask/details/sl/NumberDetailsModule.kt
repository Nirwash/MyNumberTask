package com.nirwashh.android.mynumbertask.details.sl

import com.nirwashh.android.mynumbertask.details.presentation.NumberDetailsViewModel
import com.nirwashh.android.mynumbertask.main.sl.Module
import com.nirwashh.android.mynumbertask.main.sl.ProvideNumberDetails

class NumberDetailsModule(
    private val provide: ProvideNumberDetails
) : Module<NumberDetailsViewModel> {
    override fun viewModel() = NumberDetailsViewModel(provide.provideNumberDetails())
}