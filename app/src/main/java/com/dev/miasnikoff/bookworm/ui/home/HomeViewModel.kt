package com.dev.miasnikoff.bookworm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.data.model.ImageSize
import com.dev.miasnikoff.bookworm.domain.HomeDataInteractor
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.CarouselWithTitleItem
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.bookworm.ui.home.mapper.HomeBookUiMapper
import com.dev.miasnikoff.bookworm.ui.home.model.HomeState
import com.dev.miasnikoff.bookworm.utils.extensions.addNotNull
import kotlinx.coroutines.*

class HomeViewModel(
    private val interactor: HomeDataInteractor = HomeDataInteractor(),
    private val homeBookUiMapper: HomeBookUiMapper = HomeBookUiMapper(),
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _liveData.value = HomeState.Failure(throwable.message ?: DEFAULT_ERROR_MESSAGE)
    }

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob() + exceptionHandler)
    private var job: Job? = null

    private val _liveData: MutableLiveData<HomeState> = MutableLiveData()
    val liveData: LiveData<HomeState> get() = _liveData

    init {
        getHomeData()
    }

    fun getHomeData() {
        _liveData.value = HomeState.Loading
        job = scope.launch {
            val bookOfDay = interactor.getBookOfDay()
            val newestList = interactor.getNewestList()
            val popFreeList = interactor.getPopularFreeList()
            val homeList = mutableListOf<RecyclerItem>()
            homeList.addNotNull(homeBookUiMapper.toSingleItem(bookOfDay, ImageSize.M))
            homeList.add(CarouselWithTitleItem.createGenreCarousel())
            homeList.addNotNull(CarouselWithTitleItem.createCarouselOfCategory(category = Category.NEWEST, items = homeBookUiMapper.toItemList(newestList)))
            homeList.addNotNull(CarouselWithTitleItem.createCarouselOfCategory(category = Category.FREE, items = homeBookUiMapper.toItemList(popFreeList)))
            if (homeList.size > 1) {
                _liveData.value = HomeState.Success(homeList)
            } else _liveData.value = HomeState.Failure(EMPTY_RESULT_MESSAGE)
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
        private const val EMPTY_RESULT_MESSAGE = "Nothing found!"
    }
}