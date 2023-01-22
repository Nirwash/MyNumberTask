package com.nirwashh.android.mynumbertask.main.presentation

import com.nirwashh.android.mynumbertask.numbers.presentation.BaseTest
import com.nirwashh.android.mynumbertask.random.WorkManagerWrapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest : BaseTest() {
    private lateinit var navigationCommunication: TestNavigationCommunication
    private lateinit var viewModel: MainViewModel
    private lateinit var workManagerWrapper: TestWorkManagerWrapper

    @Before
    fun setUp() {
        navigationCommunication = TestNavigationCommunication()
        workManagerWrapper = TestWorkManagerWrapper()
        viewModel = MainViewModel(workManagerWrapper, navigationCommunication)
    }


    @Test
    fun `test navigation at start`() {
        viewModel.init(true)
        assertEquals(1, navigationCommunication.count)
        assertEquals(NavigationStrategy.Replace(Screen.Numbers), navigationCommunication.strategy)
        assertEquals(1, workManagerWrapper.startCalledCount)

        viewModel.init(false)
        assertEquals(1, navigationCommunication.count)
        assertEquals(1, workManagerWrapper.startCalledCount)
    }

    private class TestWorkManagerWrapper : WorkManagerWrapper {
        var startCalledCount = 0
        override fun start() {
            startCalledCount++
        }

    }
}