package com.nirwashh.android.mynumbertask.details.presentation

import androidx.lifecycle.ViewModel
import com.nirwashh.android.mynumbertask.details.data.NumberDetails

class NumberDetailsViewModel(
    private val data: NumberDetails.Read
) : ViewModel(), NumberDetails.Read {

    override fun read() = data.read()
}