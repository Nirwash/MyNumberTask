package com.nirwashh.android.mynumbertask.numbers.data.cache

import com.nirwashh.android.mynumbertask.numbers.data.NumberData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NumbersCacheDataSourceTest {

    private lateinit var dao: TestDao
    private lateinit var dataSource: NumbersCacheDataSource

    @Before
    fun setUp() {
        dao = TestDao()
        dataSource = NumbersCacheDataSource.Base(dao, TestMapper(5))
    }

    @Test
    fun `test all numbers empty`() = runBlocking {
        val actual = dataSource.allNumbers()
        val expected = emptyList<NumberCache>()
        assertEquals(expected, actual)
    }

    @Test
    fun `test all numbers not empty`() = runBlocking {
        dao.data.add(NumberCache("1", "fact about 1", 110))
        dao.data.add(NumberCache("43", "fact about 43", 1560))
        val actualList = dataSource.allNumbers()
        val expectedList = listOf(
            NumberData("1", "fact about 1"),
            NumberData("43", "fact about 43")
        )
        actualList.forEachIndexed { index, actual ->
            assertEquals(expectedList[index], actual)
        }
    }

    @Test
    fun `test contains`() = runBlocking {
        dao.data.add(NumberCache("6", "fact 6", 55))
        val actual = dataSource.contains("6")
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun `test not contains`() = runBlocking {
        dao.data.add(NumberCache("66", "fact 66", 565))
        val actual = dataSource.contains("77")
        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `test number empty`() = runBlocking {
        val actual = dataSource.number("21")
        val expected = NumberData("", "")
        assertEquals(expected, actual)
    }

    @Test
    fun `test number not empty`() = runBlocking {
        dao.data.add(NumberCache("11", "fact11", 11))
        val actual = dataSource.number("11")
        val expected = NumberData("11", "fact11")
        assertEquals(expected, actual)
    }

    @Test
    fun `test save`() = runBlocking {
        dataSource = NumbersCacheDataSource.Base(dao, TestMapper(11))
        dataSource.saveNumber(NumberData("110", "dact11"))
        val actual = dao.data.last()
        val expected = NumberCache("110", "dact11", 11)
    }
}

private class TestDao : NumbersDao {
    val data = mutableListOf<NumberCache>()

    override suspend fun allNumbers() = data

    override suspend fun insert(number: NumberCache) {
        data.add(number)
    }

    override suspend fun number(number: String): NumberCache? {
        return data.find { it.number == number }
    }
}

private class TestMapper(private val date: Long) : NumberData.Mapper<NumberCache> {
    override fun map(id: String, fact: String) = NumberCache(id, fact, date)
}