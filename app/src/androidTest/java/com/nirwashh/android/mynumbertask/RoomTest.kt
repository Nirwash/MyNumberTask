package com.nirwashh.android.mynumbertask

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nirwashh.android.mynumbertask.numbers.data.cache.NumberCache
import com.nirwashh.android.mynumbertask.numbers.data.cache.NumbersDao
import com.nirwashh.android.mynumbertask.numbers.data.cache.NumbersDatabase
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: NumbersDatabase
    private lateinit var dao: NumbersDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, NumbersDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.numbersDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun save_to_database_and_check_it() = runBlocking {
        val number = "34"
        val actual = NumberCache(number, "fact about 34", 11)

        assertEquals(null, dao.number(number))

        dao.insert(actual)
        val numbersList = dao.allNumbers()
        val expected = numbersList[0]

        assertEquals(expected, actual)
        assertEquals(expected, dao.number(number))
    }

    @Test
    @Throws(IOException::class)
    fun save_two_times_and_check_it() = runBlocking {
        val number = NumberCache("42", "fact avout 42", 77)
        dao.insert(number)
        val numberList = dao.allNumbers()
        assertEquals(1, numberList.size)
        assertEquals(number, numberList[0])

        val new = NumberCache("42", "fact avout 42", 9)
        dao.insert(new)
        val updatedList = dao.allNumbers()
        assertEquals(1, updatedList.size)
        assertEquals(new, updatedList[0])
    }
}