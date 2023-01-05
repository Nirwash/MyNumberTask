package com.nirwashh.android.mynumbertask.numbers.data

import com.nirwashh.android.mynumbertask.numbers.domain.NoInternetConnectionException
import com.nirwashh.android.mynumbertask.numbers.domain.NumberFact
import com.nirwashh.android.mynumbertask.numbers.domain.NumbersRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseNumbersRepositoryTest {

    private lateinit var repository: NumbersRepository
    private lateinit var cloudDataSource: TestNumbersCloudDataSource
    private lateinit var cacheDataSource: TestNumbersCacheDataSource

    @Before
    fun setUp() {
        cloudDataSource = TestNumbersCloudDataSource()
        cacheDataSource = TestNumbersCacheDataSource()
        repository = BaseNumbersRepository(cloudDataSource, cacheDataSource)
    }

    @Test
    fun `test all numbers`(): Unit = runBlocking {
        cacheDataSource.replaceData(
            listOf(
                NumberData("4", "fact of 4"),
                NumberData("5", "fact of 5")
            )
        )

        val actual = cacheDataSource.allNumbers()
        val expected = listOf(
            NumberFact("4", "fact of 4"),
            NumberFact("5", "fact of 5")
        )
        actual.forEachIndexed { index, item ->
            assertEquals(expected[index], item)
        }
        assertEquals(1, cacheDataSource.allNumbersCalledCount)
    }


    @Test
    fun `test number fact not cached, success`() = runBlocking {
        cloudDataSource.expectedData(NumberData("10", "fact about 10"))
        cacheDataSource.replaceData(emptyList())

        val actual = repository.numberFact("10")
        val expected = NumberData("10", "fact about 10")

        assertEquals(expected, actual)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(1, cloudDataSource.numberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(expected, cacheDataSource.data[0])

    }

    @Test(expected = NoInternetConnectionException::class)
    fun `test number fact not cached, failure`() = runBlocking {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaceData(emptyList())

        repository.numberFact("10")
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(1, cloudDataSource.numberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test
    fun `test number fact cached`() = runBlocking {
        cloudDataSource.changeConnection(false)
        cloudDataSource.expectedData(NumberData("10", "cloud fact about 10"))
        cacheDataSource.replaceData(listOf(NumberData("10", "cache fact about 10")))

        val actual = repository.numberFact("10")
        val excepted = NumberData("10", "cache fact about 10")

        assertEquals(excepted, actual)
        assertEquals(true, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cacheDataSource.numberFactCalledList.size)
        assertEquals(cacheDataSource.numberFactCalledList[0], actual)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test
    fun `test random number fact not cached, success`() = runBlocking {
        cloudDataSource.expectedData(NumberData("10", "fact about 10"))
        cacheDataSource.replaceData(emptyList())

        val actual = repository.randomNumberFact()
        val expected = NumberData("10", "fact about 10")

        assertEquals(expected, actual)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(expected, cacheDataSource.data[0])
    }

    @Test(expected = NoInternetConnectionException::class)
    fun `test random number fact not cached, failure`() = runBlocking {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaceData(emptyList())

        repository.randomNumberFact()
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test
    fun `test random number fact cached`() = runBlocking {
        cloudDataSource.changeConnection(false)
        cloudDataSource.expectedData(NumberData("10", "cloud fact about 10"))
        cacheDataSource.replaceData(listOf(NumberData("10", "cache fact about 10")))

        val actual = repository.randomNumberFact()
        val excepted = NumberData("10", "cloud fact about 10")

        assertEquals(excepted, actual)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(true, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
    }

    private class TestNumbersCloudDataSource : NumbersCloudDataSource {
        private var thereIsConnection = false
        private var numberData = NumberData("", "")
        var numberFactCalledCount = 0
        var randomNumberFactCalledCount = 0

        fun changeConnection(connected: Boolean) {
            thereIsConnection = connected
        }

        fun expectedData(number: NumberData) {
            numberData = number
        }

        override suspend fun numberFact(number: String): NumberData {
            numberFactCalledCount++
            return if (thereIsConnection)
                numberData
            else
                throw UnknownHostException()
        }

        override suspend fun randomNumberFact(): NumberData {
            randomNumberFactCalledCount++
            return if (thereIsConnection)
                numberData
            else
                throw UnknownHostException()
        }
    }

    private class TestNumbersCacheDataSource : NumbersCacheDataSource {
        val data = mutableListOf<NumberData>()
        var numberFactCalledList = mutableListOf<String>()
        var allNumbersCalledCount = 0
        var saveNumberFactCalledCount = 0
        var containsCalledList = mutableListOf<Boolean>()

        fun replaceData(newData: List<NumberData>) {
            data.clear()
            data.addAll(newData)
        }

        override suspend fun allNumbers(): List<NumberData> {
            allNumbersCalledCount++
            return data
        }

        override suspend fun contains(number: String): Boolean {
            val result = data.find { it.matches(number) } != null
            containsCalledList.add(result)
            return result
        }

        override suspend fun numberFact(number: String): NumberData {
            numberFactCalledList.add(number)
            return data[0]
        }

        override suspend fun saveNumberFact(number: NumberData) {
            saveNumberFactCalledCount++
            data.add(number)
        }
    }
}