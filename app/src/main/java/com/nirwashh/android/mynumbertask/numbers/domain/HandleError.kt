package com.nirwashh.android.mynumbertask.numbers.domain

import com.nirwashh.android.mynumbertask.R
import com.nirwashh.android.mynumbertask.numbers.presentation.ManageResources

interface HandleError {
    fun handle(e: Exception): String

    class Base(private val manageResources: ManageResources) : HandleError {
        override fun handle(e: Exception) = manageResources.string(
            when (e) {
                is NoInternetConnectionException -> R.string.no_connection_message
                else -> R.string.service_is_unavailable
            }
        )
    }
}