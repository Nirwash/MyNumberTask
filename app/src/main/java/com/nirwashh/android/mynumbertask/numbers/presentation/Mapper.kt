package com.nirwashh.android.mynumbertask.numbers.presentation

interface Mapper<R, S> {

    fun map(source: S): R

    interface Unit<S> : Mapper<kotlin.Unit, S>
}