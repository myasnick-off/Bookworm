package com.dev.miasnikoff.feature_tabs.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.dev.miasnikoff.core.event.AppEvent
import com.dev.miasnikoff.core.event.EventBus
import com.dev.miasnikoff.core.extensions.addNotNull
import com.dev.miasnikoff.core_navigation.router.FlowRouter
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val interactor: HomeDataInteractor,
    private val homeDtoToUiMapper: HomeDtoToUiMapper,
    private val homeEntityToUiMapper: HomeEntityToUiMapper,
    private val router: FlowRouter,
    private val eventBus: EventBus
): ViewModel() {

    private var job: Job? = null

    private val _liveData: MutableLiveData<HomeState> = MutableLiveData()
    val liveData: LiveData<HomeState> get() = _liveData

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
        _liveData.value = HomeState.Loading
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
                _liveData.value = HomeState.Success(homeList)
            } else _liveData.value = HomeState.Failure(EMPTY_RESULT_MESSAGE)
        }
    }

    fun navigate(direction: NavDirections) {
        router.navigateTo(direction)
    }

    private fun updateHistoryList() {
        viewModelScope.launch {
            (liveData.value as? HomeState.Success)?.let { state ->
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
                _liveData.value = state.copy(data = newList)
            }
        }
    }

    private fun postError(message: String?) {
        _liveData.value = HomeState.Failure(message ?: DEFAULT_ERROR_MESSAGE)
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
        private const val EMPTY_RESULT_MESSAGE = "Nothing found!"
        private const val HISTORY_INDEX = 2
    }
}