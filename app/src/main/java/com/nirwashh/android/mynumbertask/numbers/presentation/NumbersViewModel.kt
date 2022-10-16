package com.nirwashh.android.mynumbertask.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nirwashh.android.mynumbertask.R
import com.nirwashh.android.mynumbertask.numbers.domain.NumbersInteractor
import com.nirwashh.android.mynumbertask.numbers.domain.NumbersResult
import kotlinx.coroutines.launch

class NumbersViewModel(
    private val handleResult: HandleNumbersRequest,
    private val manageResources: ManageResources,
    private val communications: NumbersCommunications,
    private val interactor: NumbersInteractor
) : ViewModel(), FetchNumbers, ObserveNumbers {


    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communications.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communications.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communications.observeList(owner, observer)

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            handleResult.handle(viewModelScope){
                interactor.init()
            }
        }
    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty())
            communications.showState(UiState.Error(manageResources.string(R.string.empty_number_error_message)))
        else {
            handleResult.handle(viewModelScope){
                interactor.factAboutNumber(number)
            }
        }
    }

    override fun fetchRandomNumberFact() =  handleResult.handle(viewModelScope){
        interactor.factAboutRandomNumber()
    }

}

interface FetchNumbers {
    fun init(isFirstRun: Boolean)
    fun fetchNumberFact(number: String)
    fun fetchRandomNumberFact()
}