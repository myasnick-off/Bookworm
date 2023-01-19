package com.dev.miasnikoff.bookworm.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.domain.ListInteractor
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookItem
import com.dev.miasnikoff.bookworm.ui.list.mapper.EntityToUiMapper
import com.dev.miasnikoff.bookworm.ui.list.model.PagedListState
import kotlinx.coroutines.*

class LocalListViewModel(
    private val interactor: ListInteractor = ListInteractor(),
    private val entityToUiMapper: EntityToUiMapper = EntityToUiMapper()
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _liveData.value = PagedListState.Failure(throwable.message ?: DEFAULT_ERROR_MESSAGE)
    }
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob() + exceptionHandler)
    private var job: Job? = null

    private val _liveData: MutableLiveData<PagedListState> = MutableLiveData()
    val liveData: LiveData<PagedListState> get() = _liveData

    private var currentList: MutableList<RecyclerItem> = mutableListOf()
    private var currentCategory: Category = Category.LAST_VIEWED

    fun getInitialPage(category: Category) {
        currentCategory = category
        _liveData.value = PagedListState.Loading
        currentList.clear()
        getBookList()
    }

    private fun getBookList() {
        when (currentCategory) {
            Category.LAST_VIEWED -> getAllHistory()
            Category.FAVORITE -> getAllFavorite()
            else -> {}
        }
    }

    private fun getAllHistory() {
        job?.cancel()
        job = scope.launch {
            val newList = mutableListOf<RecyclerItem>().apply {
                addAll(entityToUiMapper.toItemList(interactor.getHistory()))
                sortBy { it.id }
            }
            currentList = newList
            _liveData.value = if (currentList.isEmpty()) {
                PagedListState.Failure(EMPTY_RESULT_MESSAGE)
            } else {
                PagedListState.Success(currentList, false)
            }
        }
    }

    private fun getAllFavorite() {
        job?.cancel()
        job = scope.launch {
            val newList = mutableListOf<RecyclerItem>().apply {
                addAll(entityToUiMapper.toItemList(interactor.getFavorite()))
            }
            currentList = newList
            _liveData.value = if (currentList.isEmpty()) {
                PagedListState.Failure(EMPTY_RESULT_MESSAGE)
            } else {
                PagedListState.Success(currentList, false)
            }
        }
    }

    fun setFavorite(itemId: String) {
        _liveData.value = PagedListState.MoreLoading
        scope.launch {
            val index = currentList.indexOfFirst { it.id == itemId }
            val bookItem = (currentList.firstOrNull { it.id == itemId } as? BookItem)
            if (index > -1 && bookItem != null) {
                if (bookItem.isFavorite) {
                    interactor.removeFromFavorite(bookItem)
                } else {
                    interactor.saveInFavorite(bookItem)
                }
            }
            getBookList()
        }
    }

    fun removeHistory() {
        scope.launch {
            interactor.removeAllHistory()
            getAllHistory()
        }
    }

    fun removeFavorites() {
        scope.launch {
            interactor.removeAllFavorite()
            getAllFavorite()
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    fun removeFromLocal(itemId: String) {
        _liveData.value = PagedListState.MoreLoading
        scope.launch {
            val index = currentList.indexOfFirst { it.id == itemId }
            val bookItem = (currentList.firstOrNull { it.id == itemId } as? BookItem)
            if (index > -1 && bookItem != null) {
                if (currentCategory == Category.FAVORITE) {
                    interactor.removeFromFavorite(bookItem)
                } else {
                    interactor.removeFromHistory(bookItem)
                }
            }
            getBookList()
        }

    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
        private const val EMPTY_RESULT_MESSAGE = "Nothing found!"
    }
}