package com.nirwashh.android.mynumbertask.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.nirwashh.android.mynumbertask.main.presentation.NavigationCommunication
import com.nirwashh.android.mynumbertask.main.presentation.NavigationStrategy

abstract class BaseTest {
    protected class TestNumbersCommunications : NumbersCommunications {
        val progressCalledList = mutableListOf<Int>()
        val stateCalledList = mutableListOf<UiState>()
        val numbersList = mutableListOf<NumberUi>()
        var timesShowList = 0
        override fun showProgress(show: Int) {
            progressCalledList.add(show)
        }

        override fun showState(uiState: UiState) {
            stateCalledList.add(uiState)
        }

        override fun showList(list: List<NumberUi>) {
            timesShowList++
            numbersList.addAll(list)
        }

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) = Unit

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) = Unit

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) = Unit

    }

    protected class TestNavigationCommunication : NavigationCommunication.Mutable {
        lateinit var strategy: NavigationStrategy
        var count = 0
        override fun observe(owner: LifecycleOwner, observer: Observer<NavigationStrategy>) = Unit

        override fun map(source: NavigationStrategy) {
            strategy = source
            count++
        }

    }
}