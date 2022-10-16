package com.nirwashh.android.mynumbertask.numbers.presentation

import org.junit.Assert.*
import org.junit.Test

class NumbersViewModelTest {

    /**
     * Initial test
     * получаем данные при запуске и отображаем их
     * затем пробуем получить другие данные
     * затем перезапускаем и ждем результата
     */
    @Test
    fun `test init and re-init`() {
        val communications = TestNumbersCommunications()
        val interactor = TestNumberInteractor()
        //1.initialize
        val viewModel = NumversViewModel(communications, interactor)
        interactor.changeExpectedResult(NumbersResult.Success())
        //2.action
        viewModel.init(isFirstRun = true)
        //3.check
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(0, communications.numbersList.size)
        assertEquals(1, communications.timesShowList)


        //get some data
        interactor.changeExpectedResult(NumbersResult.Failure())
        viewModel.fetchRandomNumberData()

        assertEquals(3, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error(/* todo message */), communications.stateCalledList[1])
        assertEquals(1, communications.timesShowList)

        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(1, communications.timesShowList)

    }

    /**
     * пытаемся получить информацию без числа
     */
    @Test
    fun `fact about empty number`() {
        val communications = TestNumbersCommunications()
        val interactor = TestNumberInteractor()
        val viewModel = NumversViewModel(communications, interactor)
        viewModel.fetchFact("")
        assertEquals(0, interactor.fetchAboutNumberCalledList.size)
        assertEquals(0, communications.progressCalledList.size)

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Error("Entered number is empty"), communications.stateCalledList[0])

        assertEquals(0, communications.timesShowList)

    }
    
    /**
     * пытаемся получить информацию с рандомным числом
     */
    @Test
    fun `fact about some number`() {
        val communications = TestNumbersCommunications()
        val interactor = TestNumberInteractor()
        val viewModel = NumversViewModel(communications, interactor)
        interactor.changeExpectedResult(NumversResult.Success(listOf(Number("45", "fact about 45"))))
        viewModel.fetchFact("45")
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchAboutNumberCalledList.size)
        assertEquals(Number("45", "fact about 45"), interactor.fetchAboutNumberCalledList[0])

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("45", "fact about 45"), communications.numbersList[0])

    }

    private class TestNumbersCommunications : NumbersCommunications {
        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<Boolean>()
        val numbersList = mutableListOf<NumberUi>()
        var timesShowList = 0
        override fun showProgress(show: Boolean) {
            progressCalledList.add(show)
        }

        override fun showState(state: UiState) {
            stateCalledList.add(state)
        }

        override fun showList(list: List<NumberUi>) {
            timesShowList++
            numbersList.addAll(list)
        }

     }

    private class TestNumberInteractor : NumberInteractor {
        private var result: NumbersResult = NumbersResult.Success()
        val initCalledList = mutableListOf<NumbersResult>()
        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()
        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }
        override suspend fun init() : NumbersResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun factAboutNumber(number: String) : NumbersResult {
            fetchAboutNumberCalledList.add(result)
            return result

        }

        override suspend fun factAboutRandomNumber() : NumbersResult {
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }
    }
}