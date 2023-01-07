package com.nirwashh.android.mynumbertask.numbers.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NumbersDao {

    @Query("SELECT * FROM numbers_table ORDER BY date ASC")
    suspend fun allNumbers(): List<NumberCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(number: NumberCache)

    @Query("SELECT * FROM numbers_table where number = :number")
    suspend fun number(number: String): NumberCache?
}