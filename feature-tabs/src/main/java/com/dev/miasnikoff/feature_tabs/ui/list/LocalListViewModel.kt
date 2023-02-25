package com.dev.miasnikoff.feature_tabs.ui.list

import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.dev.miasnikoff.core.event.AppEvent
import com.dev.miasnikoff.core.event.EventBus
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
import kotlinx.coroutines.flow.collectLatest

class LocalListViewModel @AssistedInject constructor(
    private val interactor: ListInteractor,
    private val entityToUiMapper: EntityToUiMapper,
    private val router: FlowRouter,
    private val eventBus: EventBus,
    @Assisted private val category: Category
) : ViewModel() {

    private var job: Job? = null

    private val _liveData: MutableLiveData<PagedListState> = MutableLiveData()
    val liveData: LiveData<PagedListState> get() = _liveData

    private var currentList: MutableList<RecyclerItem> = mutableListOf()

    init {
        getInitialPage()
        handleAppEvents()
    }

    private fun handleAppEvents() {
        viewModelScope.launch {
            eventBus.events.collectLatest { event ->
                when(event) {
                    is AppEvent.FavoriteUpdate, is AppEvent.HistoryUpdate -> getBookList()
                    else -> {}
                }
            }
        }
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
        job = viewModelScope.launch {
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
        job = viewModelScope.launch {
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
        viewModelScope.launch {
            val index = currentList.indexOfFirst { it.id == itemId }
            val bookItem = (currentList.firstOrNull { it.id == itemId } as? BookItem)
            if (index > -1 && bookItem != null) {
                if (bookItem.isFavorite) {
                    interactor.removeFromFavorite(bookItem)
                } else {
                    interactor.saveInFavorite(bookItem)
                }
            }
            eventBus.emitEvent(AppEvent.FavoriteUpdate(itemId))
        }
    }

    fun removeHistory() {
        viewModelScope.launch {
            interactor.removeAllHistory()
            eventBus.emitEvent(AppEvent.HistoryUpdate())
        }
    }

    fun removeFavorites() {
        viewModelScope.launch {
            interactor.removeAllFavorite()
            eventBus.emitEvent(AppEvent.FavoriteUpdate())
        }
    }

    fun removeFromLocal(itemId: String) {
        _liveData.value = PagedListState.MoreLoading
        viewModelScope.launch {
            val index = currentList.indexOfFirst { it.id == itemId }
            val bookItem = (currentList.firstOrNull { it.id == itemId } as? BookItem)
            if (index > -1 && bookItem != null) {
                if (category == Category.FAVORITE) {
                    interactor.removeFromFavorite(bookItem)
                    eventBus.emitEvent(AppEvent.FavoriteUpdate(itemId))
                } else {
                    interactor.removeFromHistory(bookItem)
                    eventBus.emitEvent(AppEvent.HistoryUpdate(itemId))
                }
            }
        }
    }

    fun navigate(direction: NavDirections) {
        router.navigateTo(direction)
    }

    fun back() {
        router.back()
    }

    companion object {
        private const val EMPTY_RESULT_MESSAGE = "Nothing found!"
    }
}

@Suppress("UNCHECKED_CAST")
class LocalListViewModelFactory @AssistedInject constructor(
    private val interactor: ListInteractorImpl,
    private val entityToUiMapper: EntityToUiMapper,
    private val router: FlowRouter,
    private val eventBus: EventBus,
    @Assisted private val category: Category
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        assert(modelClass == LocalListViewModel::class.java)
        return LocalListViewModel(interactor, entityToUiMapper, router, eventBus, category) as T
    }
}

@AssistedFactory
interface LocalListViewModelAssistedFactory {
    fun create(@Assisted category: Category): LocalListViewModelFactory
}