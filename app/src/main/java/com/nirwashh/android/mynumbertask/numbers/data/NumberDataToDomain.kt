package com.nirwashh.android.mynumbertask.numbers.data

import com.nirwashh.android.mynumbertask.numbers.domain.NumberFact

class NumberDataToDomain : NumberData.Mapper<NumberFact> {
    override fun map(id: String, fact: String) = NumberFact(id, fact)
}