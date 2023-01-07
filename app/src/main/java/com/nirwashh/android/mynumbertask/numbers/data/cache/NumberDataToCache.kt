package com.nirwashh.android.mynumbertask.numbers.data.cache

import com.nirwashh.android.mynumbertask.numbers.data.NumberData

class NumberDataToCache : NumberData.Mapper<NumberCache> {
    override fun map(id: String, fact: String) = NumberCache(id, fact, System.currentTimeMillis())
}