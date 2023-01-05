package com.nirwashh.android.mynumbertask.numbers.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class NumbersInteractorTest {

    private lateinit var repository: TestNumbersRepository
    private lateinit var interactor: NumbersInteractor

    @Before
    fun setUp() {
        repository = TestNumbersRepository()
        interactor = NumbersInteractor.Base(repository)
    }

    @Test
    fun `init`(): Unit = runBlocking {
        repository.changeExpectedList(listOf(NumberFact("6", "fact about 6")))

        val actual = interactor.init()
        val expected = NumbersResult.Success(listOf(NumberFact("6", "fact about 6")))

        assertEquals(expected, actual)
        assertEquals(1, repository.allNumbersCalledCount)
    }

    @Test
    fun `test about number success`(): Unit = runBlocking {
        repository.changeExpectedErrorGetNumberFact(false)
        repository.changeExpectedNumberFact(NumberFact("7", "fact about 7"))

        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Success(listOf(NumberFact("7", "fact about 7")))

        assertEquals(expected, actual)
        assertEquals("7", repository.numberFactCalledList[0])
        assertEquals(1, repository.numberFactCalledList.size)
    }

    @Test
    fun `test about number error`(): Unit = runBlocking {
        repository.changeExpectedErrorGetNumberFact(true)

        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Failure("no internet connection")

        assertEquals(expected, actual)
        assertEquals("7", repository.numberFactCalledList[0])
        assertEquals(1, repository.numberFactCalledList.size)
    }

    @Test
    fun `test about random number success`(): Unit = runBlocking {
        repository.changeExpectedErrorGetRandomNumberFact(false)
        repository.changeExpectedRandomNumberFact(NumberFact("7", "fact about 7"))

        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Success(listOf(NumberFact("7", "fact about 7")))

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumberFactCalledList.size)
    }

    @Test
    fun `test about random number error`(): Unit = runBlocking {
        repository.changeExpectedErrorGetRandomNumberFact(true)

        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Failure("no internet connection")

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumberFactCalledList.size)
    }


    private class TestNumbersRepository() : NumbersRepository() {
        private val allNumbers = mutableListOf<NumberFact>()
        private var numberFact = NumberFact("", "")
        private var randomNumberFact = NumberFact("", "")
        private var errorGetNumberFact = false
        private var errorGetRandomNumberFact = false

        var allNumbersCalledCount = 0
        var numberFactCalledList = mutableListOf<String>()
        var randomNumberFactCalledList = mutableListOf<String>()


        fun changeExpectedList(list: List<NumberFact>) {
            allNumbers.clear()
            allNumbers.addAll(list)
        }

        fun changeExpectedNumberFact(numberFact: NumberFact) {
            this.numberFact = numberFact
        }

        fun changeExpectedRandomNumberFact(numberFact: NumberFact) {
            this.randomNumberFact = numberFact
        }

        fun changeExpectedErrorGetNumberFact(error: Boolean) {
            errorGetNumberFact = error
        }

        fun changeExpectedErrorGetRandomNumberFact(error: Boolean) {
            errorGetRandomNumberFact = error
        }

        override fun allNumbers(): List<NumberFact> {
            allNumbersCalledCount++
            return allNumbers
        }

        override fun numberFact(number: String): NumberFact {
            numberFactCalledList.add(number)
            if (errorGetNumberFact) {
                throw NoInternetConnectionException
            }
            return numberFact
        }

        override fun randomNumberFact(): NumberFact {
            randomNumberFactCalledList.add("")
            if (errorGetRandomNumberFact) {
                throw NoInternetConnectionException
            }
            return randomNumberFact
        }
    }
}