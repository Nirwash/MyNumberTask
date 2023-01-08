package com.nirwashh.android.mynumbertask.numbers.presentation

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


sealed class UiState {
    abstract fun apply(inputLayout: TextInputLayout, editText: TextInputEditText)

    abstract class AbstractError(private val message: String, private val errorEnabled: Boolean) :
        UiState() {
        override fun apply(inputLayout: TextInputLayout, editText: TextInputEditText) =
            with(inputLayout) {
                isErrorEnabled = errorEnabled
                error = message
            }
    }

    class Success : UiState() {
        override fun apply(inputLayout: TextInputLayout, editText: TextInputEditText) =
            editText.setText("")


        override fun equals(other: Any?): Boolean {
            return if (other is Success) true else super.equals(other)
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    data class ShowError(private val message: String) : AbstractError(message, true)
    class ClearError() : AbstractError("", false)
}