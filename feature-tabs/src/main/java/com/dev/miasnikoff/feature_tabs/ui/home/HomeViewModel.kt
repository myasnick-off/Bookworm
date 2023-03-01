package com.dev.miasnikoff.feature_tabs.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dev.miasnikoff.core.event.AppEvent
import com.dev.miasnikoff.core.event.EventBus
import com.dev.miasnikoff.core.extensions.addNotNull
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageSize
import com.dev.miasnikoff.feature_tabs.domain.interactor.HomeDataInteractor
import com.dev.miasnikoff.feature_tabs.domain.model.onFailure
import com.dev.miasnikoff.feature_tabs.domain.model.onSuccess
import com.dev.miasnikoff.feature_tabs.ui.base.BaseListViewModel
import com.dev.miasnikoff.feature_tabs.ui.base.ListState
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.CarouselWithTitleItem
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.feature_tabs.ui.home.mapper.HomeDtoToUiMapper
import com.dev.miasnikoff.feature_tabs.ui.home.mapper.HomeEntityToUiMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val interactor: HomeDataInteractor,
    private val homeDtoToUiMapper: HomeDtoToUiMapper,
    private val homeEntityToUiMapper: HomeEntityToUiMapper,
    router: FlowRouter,
    private val eventBus: EventBus
) : BaseListViewModel(router) {

    init {
        getInitialData()
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

    override fun getInitialData() {
        mutableStateFlow.value = ListState.EmptyLoading
        getHomeData()
    }

    private fun getHomeData() {
        job?.cancel()
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
                mutableStateFlow.value = ListState.Success(homeList)
            } else mutableStateFlow.value = ListState.Failure
        }
    }

    private fun updateHistoryList() {
        viewModelScope.launch {
            (stateFlow.value as? ListState.Success)?.let { state ->
                val newList: MutableList<RecyclerItem> = mutableListOf()
                newList.addAll(state.data)
                val historyList = homeEntityToUiMapper.toItemList(interactor.getHistory())
                val index = newList.indexOfFirst {
                    it is CarouselWithTitleItem && it.category == Category.LAST_VIEWED
                }
                when {
                    index > INVALID_INDEX && historyList.isEmpty() -> newList.removeAt(index)
                    index > INVALID_INDEX && historyList.isNotEmpty() -> newList[index] = (newList[index] as CarouselWithTitleItem).copy(items = historyList)
                    else -> newList.addNotNull(HISTORY_INDEX, CarouselWithTitleItem.createCarouselOfCategory(category = Category.LAST_VIEWED, items = historyList))
                }
                mutableStateFlow.value = state.copy(data = newList)
            }
        }
    }

    private fun postError(message: String?) {
        Log.e("###", message ?: "")
        mutableStateFlow.value = ListState.Failure
    }

    companion object {
        private const val INVALID_INDEX = 2
        private const val HISTORY_INDEX = 2
    }
}