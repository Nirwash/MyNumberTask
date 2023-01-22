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
import com.nirwashh.android.mynumbertask.numbers.domain.*
import com.nirwashh.android.mynumbertask.numbers.presentation.*

class NumbersModule(
    private val core: Core,
    private val provideRepository: ProvideNumbersRepository
) : Module<NumbersViewModel.Base> {
    override fun viewModel(): NumbersViewModel.Base {
        val repository = provideRepository.provideNumbersRepository()
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

        val handleRequest = HandleRequest.Base(
            repository = repository,
            handleError = HandleError.Base(core)
        )
        val interactor = NumbersInteractor.Base(
            repository = repository,
            handleRequest = handleRequest,
            numberDetails = core.provideNumberDetails()
        )
        return NumbersViewModel.Base(
            handleResult = handleResult,
            manageResources = core,
            communications = communications,
            interactor = interactor,
            navigationCommunication = core.provideNavigation(),
            detailsMapper = DetailUi()
        )
    }
}

interface ProvideNumbersRepository {
    fun provideNumbersRepository(): NumbersRepository

    class Base(private val core: Core) : ProvideNumbersRepository {
        override fun provideNumbersRepository(): NumbersRepository {
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
            return BaseNumbersRepository(
                cloudDataSource = cloudDataSource,
                cacheDataSource = cacheDataSource,
                handleDataRequest = handleDataRequest,
                mapperToDomain = NumberDataToDomain()
            )
        }
    }
}