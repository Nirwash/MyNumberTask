package com.nirwashh.android.mynumbertask.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.nirwashh.android.mynumbertask.numbers.domain.NumberFact
import com.nirwashh.android.mynumbertask.numbers.domain.NumberInteractor
import com.nirwashh.android.mynumbertask.numbers.domain.NumberUiMapper
import com.nirwashh.android.mynumbertask.numbers.domain.NumbersResult
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
        val viewModel = NumbersViewModel(
            communications,
            interactor,
            NumbersResultMapper(communications, NumberUiMapper())
        )
        interactor.changeExpectedResult(NumbersResult.Success())
        //2.action
        viewModel.init(isFirstRun = true)
        //3.check
        assertEquals(true, communications.progressCalledList[0])
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])


        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.timesShowList)


        //get some data
        interactor.changeExpectedResult(NumbersResult.Failure("no internet connection"))
        viewModel.fetchRandomNumberFact()

        assertEquals(3, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error("no internet connection"), communications.stateCalledList[1])
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
        val viewModel = NumbersViewModel(
            communications,
            interactor,
            NumbersResultMapper(communications, NumberUiMapper())
        )
        viewModel.fetchNumberFact("")
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
        val viewModel = NumbersViewModel(
            communications,
            interactor,
            NumbersResultMapper(communications, NumberUiMapper())
        )
        interactor.changeExpectedResult(
            NumbersResult.Success(
                listOf(
                    NumberFact(
                        "45",
                        "fact about 45"
                    )
                )
            )
        )
        viewModel.fetchNumberFact("45")
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchAboutNumberCalledList.size)
        assertEquals(NumberFact("45", "fact about 45"), interactor.fetchAboutNumberCalledList[0])

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("45", "fact about 45"), communications.numbersList[0])

    }

    private class TestNumbersCommunications : NumbersCommunications {
        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<UiState>()
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

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) = Unit

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) = Unit

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) = Unit

    }

    private class TestNumberInteractor : NumberInteractor {
        private var result: NumbersResult = NumbersResult.Success()
        val initCalledList = mutableListOf<NumbersResult>()
        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()
        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun factAboutNumber(number: String): NumbersResult {
            fetchAboutNumberCalledList.add(result)
            return result

        }

        override suspend fun factAboutRandomNumber(): NumbersResult {
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }
    }
}