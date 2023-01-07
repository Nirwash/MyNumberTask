package com.nirwashh.android.mynumbertask.numbers.data.cache

import android.content.Context
import androidx.room.Room

interface CacheModule {

    fun provideDatabase(): NumbersDatabase

    class Base(private val context: Context) : CacheModule {
        private val database by lazy {
            return@lazy Room.databaseBuilder(
                context,
                NumbersDatabase::class.java,
                "numbers database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        override fun provideDatabase(): NumbersDatabase = database
    }
}