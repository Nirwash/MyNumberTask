package com.nirwashh.android.mynumbertask.main.presentation

import com.nirwashh.android.mynumbertask.details.presentation.NumberDetailsFragment
import com.nirwashh.android.mynumbertask.numbers.presentation.NumbersFragment

sealed class Screen {
    abstract fun fragment(): Class<out BaseFragment<*>>

    object Details : Screen() {
        override fun fragment(): Class<out BaseFragment<*>> = NumberDetailsFragment::class.java
    }

    object Numbers : Screen() {
        override fun fragment(): Class<out BaseFragment<*>> = NumbersFragment::class.java
    }
}