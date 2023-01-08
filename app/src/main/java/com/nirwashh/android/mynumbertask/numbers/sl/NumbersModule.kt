package com.nirwashh.android.mynumbertask.numbers.sl

import com.nirwashh.android.mynumbertask.main.sl.Core
import com.nirwashh.android.mynumbertask.main.sl.Module
import com.nirwashh.android.mynumbertask.numbers.data.BaseNumbersRepository
import com.nirwashh.android.mynumbertask.numbers.data.HandleDataRequest
import com.nirwashh.android.mynumbertask.numbers.data.HandleDomainError
import com.nirwashh.android.mynumbertask.numbers.data.NumberDataToDomain
import com.nirwashh.android.mynumbertask.numbers.data.cache.NumberDataToCache
import com.nirwashh.android.mynumbertask.numbers.data.cache.NumbersCacheDataSource
import com.nirwashh.android.mynumbertask.numbers.data.cloud.NumbersCloudDataSource
import com.nirwashh.android.mynumbertask.numbers.data.cloud.NumbersService
import com.nirwashh.android.mynumbertask.numbers.domain.HandleError
import com.nirwashh.android.mynumbertask.numbers.domain.HandleRequest
import com.nirwashh.android.mynumbertask.numbers.domain.NumberUiMapper
import com.nirwashh.android.mynumbertask.numbers.domain.NumbersInteractor
import com.nirwashh.android.mynumbertask.numbers.presentation.*

class NumbersModule(private val core: Core) : Module<NumbersViewModel> {
    override fun viewModel(): NumbersViewModel {
        val communications = NumbersCommunications.Base(
            progress = ProgressCommunication.Base(),
            state = NumbersStateCommunication.Base(),
            numbersList = NumbersListCommunication.Base()
        )
        val handleResult = HandleNumbersRequest.Base(
            dispatchers = core.provideDispatchers(),
            communications = communications,
            numberResultMapper = NumbersResultMapper(communications, NumberUiMapper())
        )
        val cloudDataSource = NumbersCloudDataSource.Base(
            core.service(NumbersService::class.java)
        )
        val cacheDataSource = NumbersCacheDataSource.Base(
            dao = core.provideDatabase().numbersDao(),
            dataToCache = NumberDataToCache()
        )
        val handleDataRequest = HandleDataRequest.Base(
            cacheDataSource = cacheDataSource,
            mapperToDomain = NumberDataToDomain(),
            handleError = HandleDomainError()
        )
        val repository = BaseNumbersRepository(
            cloudDataSource = cloudDataSource,
            cacheDataSource = cacheDataSource,
            handleDataRequest = handleDataRequest,
            mapperToDomain = NumberDataToDomain()
        )
        val handleRequest = HandleRequest.Base(
            repository = repository,
            handleError = HandleError.Base(core)
        )
        val interactor = NumbersInteractor.Base(
            repository = repository,
            handleRequest = handleRequest
        )
        return NumbersViewModel(
            handleResult = handleResult,
            manageResources = core,
            communications = communications,
            interactor = interactor
        )
    }
}