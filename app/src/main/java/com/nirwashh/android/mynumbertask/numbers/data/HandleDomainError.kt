package com.nirwashh.android.mynumbertask.numbers.data

import com.nirwashh.android.mynumbertask.numbers.domain.HandleError
import com.nirwashh.android.mynumbertask.numbers.domain.NoInternetConnectionException
import com.nirwashh.android.mynumbertask.numbers.domain.ServiceUnavailableException
import java.net.UnknownHostException

class HandleDomainError : HandleError<Exception> {
    override fun handle(e: Exception) =
        when (e) {
            is UnknownHostException -> NoInternetConnectionException()
            else -> ServiceUnavailableException()
        }

}