package com.nirwashh.android.mynumbertask.details.data

interface NumberDetails {
    interface Save {
        fun save(data: String)
    }

    interface Read {
        fun read(): String
    }

    interface Mutable : Save, Read

    class Base : Mutable {
        private var value = ""

        override fun save(data: String) {
            value = data
        }

        override fun read() = value
    }
}