package com.nirwashh.android.mynumbertask.numbers.domain

abstract class DomainException : IllegalStateException()

class NoInternetConnectionException: DomainException()
class ServiceUnavailableException: DomainException()