package com.dev.miasnikoff.bookworm.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.data.model.ImageSize
import com.dev.miasnikoff.bookworm.domain.HomeDataInteractor
import com.dev.miasnikoff.bookworm.presentation.home.mapper.HomeBookMapper
import com.dev.miasnikoff.bookworm.presentation.home.mapper.HomeBookUiMapper
import com.dev.miasnikoff.bookworm.presentation.home.model.Genre
import com.dev.miasnikoff.bookworm.presentation.home.model.HomeData
import com.dev.miasnikoff.bookworm.presentation.home.model.HomeState
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
            if (bookOfDay != null) {
                _liveData.value = HomeState.Success(HomeData(
                    bookOfDay = homeBookUiMapper.toItem(bookOfDay, ImageSize.M),
                    popularGenres = Genre.createList(),
                    lastSeen= listOf(),
                    newest = homeBookUiMapper.toItemList(newestList),
                    popularFree = homeBookUiMapper.toItemList(popFreeList)
                ))
            } else _liveData.value = HomeState.Failure(EMPTY_RESULT_MESSAGE)
        }
    }

    companion object {
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
        private const val EMPTY_RESULT_MESSAGE = "Nothing found!"
    }
}