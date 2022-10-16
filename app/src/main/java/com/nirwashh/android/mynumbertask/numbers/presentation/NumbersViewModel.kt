package com.nirwashh.android.mynumbertask.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.nirwashh.android.mynumbertask.numbers.domain.NumberInteractor
import com.nirwashh.android.mynumbertask.numbers.domain.NumbersResult

class NumbersViewModel(
    private val communications: NumbersCommunications,
    private val interactor: NumberInteractor,
    private val numberResultMapper: NumbersResult.Mapper<Unit>
) : FetchNumbers, ObserveNumbers {


    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communications.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communications.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communications.observeList(owner, observer)

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            communications.showProgress(true)
            viewModelScope.launch {
                val result = interactor.init()
                communications.showProgress(false)
                result.map(numberResultMapper)
            }
        }
    }

    override fun fetchNumberFact(number: String) {
        TODO("Not yet implemented")
    }

    override fun fetchRandomNumberFact() {
        TODO("Not yet implemented")
    }

}

interface FetchNumbers {
    fun init(isFirstRun: Boolean)
    fun fetchNumberFact(number: String)
    fun fetchRandomNumberFact()
}