package com.nirwashh.android.mynumbertask.main.presentation

import com.nirwashh.android.mynumbertask.numbers.presentation.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest : BaseTest() {
    private lateinit var navigationCommunication: TestNavigationCommunication
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        navigationCommunication = TestNavigationCommunication()
        viewModel = MainViewModel(navigationCommunication)
    }

    @Test
    fun `test navigation at start`() {
        viewModel.init(true)
        assertEquals(1, navigationCommunication.count)
        assertEquals(true, navigationCommunication.strategy is NavigationStrategy.Replace)

        viewModel.init(false)
        assertEquals(1, navigationCommunication.count)
    }
}