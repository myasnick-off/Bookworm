package com.dev.miasnikoff.feature_tabs.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractor
import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractorImpl
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import com.dev.miasnikoff.feature_tabs.ui.list.mapper.EntityToUiMapper
import com.dev.miasnikoff.feature_tabs.ui.list.model.PagedListState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*

class LocalListViewModel @AssistedInject constructor(
    private val interactor: ListInteractor,
    private val entityToUiMapper: EntityToUiMapper,
    private val router: FlowRouter,
    @Assisted private val category: Category
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _liveData.value = PagedListState.Failure(throwable.message ?: DEFAULT_ERROR_MESSAGE)
    }
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob() + exceptionHandler)
    private var job: Job? = null

    private val _liveData: MutableLiveData<PagedListState> = MutableLiveData()
    val liveData: LiveData<PagedListState> get() = _liveData

    private var currentList: MutableList<RecyclerItem> = mutableListOf()

    init {
        getInitialPage()
    }

    fun getInitialPage() {
        _liveData.value = PagedListState.Loading
        currentList.clear()
        getBookList()
    }

    private fun getBookList() {
        when (category) {
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
                if (category == Category.FAVORITE) {
                    interactor.removeFromFavorite(bookItem)
                } else {
                    interactor.removeFromHistory(bookItem)
                }
            }
            getBookList()
        }
    }

    fun navigate(direction: NavDirections) {
        router.navigateTo(direction)
    }

    fun back() {
        router.back()
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
        private const val EMPTY_RESULT_MESSAGE = "Nothing found!"
    }
}

@Suppress("UNCHECKED_CAST")
class LocalListViewModelFactory @AssistedInject constructor(
    private val interactor: ListInteractorImpl,
    private val entityToUiMapper: EntityToUiMapper,
    private val router: FlowRouter,
    @Assisted private val category: Category
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        assert(modelClass == LocalListViewModel::class.java)
        return LocalListViewModel(interactor, entityToUiMapper, router, category) as T
    }
}

@AssistedFactory
interface LocalListViewModelAssistedFactory {
    fun create(@Assisted category: Category): LocalListViewModelFactory
}