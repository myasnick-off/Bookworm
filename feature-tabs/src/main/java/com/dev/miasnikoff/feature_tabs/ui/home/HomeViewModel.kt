package com.dev.miasnikoff.feature_tabs.ui.home

import androidx.lifecycle.viewModelScope
import com.dev.miasnikoff.core.event.AppEvent
import com.dev.miasnikoff.core.event.EventBus
import com.dev.miasnikoff.core.extensions.addNotNull
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.BaseViewModel
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageSize
import com.dev.miasnikoff.feature_tabs.domain.interactor.HomeDataInteractor
import com.dev.miasnikoff.feature_tabs.domain.model.onFailure
import com.dev.miasnikoff.feature_tabs.domain.model.onSuccess
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.CarouselWithTitleItem
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.feature_tabs.ui.home.mapper.HomeDtoToUiMapper
import com.dev.miasnikoff.feature_tabs.ui.home.mapper.HomeEntityToUiMapper
import com.dev.miasnikoff.feature_tabs.ui.home.model.HomeState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val interactor: HomeDataInteractor,
    private val homeDtoToUiMapper: HomeDtoToUiMapper,
    private val homeEntityToUiMapper: HomeEntityToUiMapper,
    router: FlowRouter,
    private val eventBus: EventBus
) : BaseViewModel(router) {

    private var job: Job? = null

    private val mutableStateFlow = MutableStateFlow<HomeState>(HomeState.Empty)
    val stateFlow = mutableStateFlow.asStateFlow()

    init {
        getHomeData()
        handleAppEvents()
    }

    private fun handleAppEvents() {
        viewModelScope.launch {
            eventBus.events.collectLatest { event ->
                when(event) {
                    is AppEvent.HistoryUpdate -> updateHistoryList()
                    else -> {}
                }
            }
        }
    }

    fun getHomeData() {
        mutableStateFlow.value = HomeState.Loading
        job = viewModelScope.launch {
            val homeList = mutableListOf<RecyclerItem>()
            interactor.getBookOfDay()
                .onSuccess { bookDto ->
                    homeList.addNotNull(homeDtoToUiMapper.toSingleItem(bookDto, ImageSize.M))
                }
                .onFailure(::postError)

            homeList.add(CarouselWithTitleItem.createGenreCarousel())

            val historyList = homeEntityToUiMapper.toItemList(interactor.getHistory())
            homeList.addNotNull(
                CarouselWithTitleItem.createCarouselOfCategory(
                    category = Category.LAST_VIEWED,
                    items = historyList
                )
            )

            interactor.getNewestList()
                .onSuccess { newestList ->
                    homeList.addNotNull(
                        CarouselWithTitleItem.createCarouselOfCategory(
                            category = Category.NEWEST,
                            items = homeDtoToUiMapper.toItemList(newestList)
                        )
                    )
                }
                .onFailure(::postError)

            interactor.getPopularFreeList()
                .onSuccess { popFreeList ->
                    homeList.addNotNull(
                        CarouselWithTitleItem.createCarouselOfCategory(
                            category = Category.FREE,
                            items = homeDtoToUiMapper.toItemList(popFreeList)
                        )
                    )
                }
                .onFailure(::postError)

            if (homeList.size > 1) {
                mutableStateFlow.value = HomeState.Success(homeList)
            } else mutableStateFlow.value = HomeState.Failure(EMPTY_RESULT_MESSAGE)
        }
    }

    private fun updateHistoryList() {
        viewModelScope.launch {
            (stateFlow.value as? HomeState.Success)?.let { state ->
                val newList: MutableList<RecyclerItem> = mutableListOf()
                newList.addAll(state.data)
                val historyList = homeEntityToUiMapper.toItemList(interactor.getHistory())
                val index = newList.indexOfFirst {
                    it is CarouselWithTitleItem && it.category == Category.LAST_VIEWED
                }
                when {
                    index > -1 && historyList.isEmpty() -> newList.removeAt(index)
                    index > -1 && historyList.isNotEmpty() -> newList[index] = (newList[index] as CarouselWithTitleItem).copy(items = historyList)
                    else -> newList.addNotNull(HISTORY_INDEX, CarouselWithTitleItem.createCarouselOfCategory(category = Category.LAST_VIEWED, items = historyList))
                }
                mutableStateFlow.value = state.copy(data = newList)
            }
        }
    }

    private fun postError(message: String?) {
        mutableStateFlow.value = HomeState.Failure(message ?: DEFAULT_ERROR_MESSAGE)
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
        private const val EMPTY_RESULT_MESSAGE = "Nothing found!"
        private const val HISTORY_INDEX = 2
    }
}