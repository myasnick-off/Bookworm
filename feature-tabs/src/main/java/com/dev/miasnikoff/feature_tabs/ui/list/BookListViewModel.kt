package com.dev.miasnikoff.feature_tabs.ui.list

import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.dev.miasnikoff.core.event.AppEvent
import com.dev.miasnikoff.core.event.EventBus
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractor
import com.dev.miasnikoff.feature_tabs.domain.model.*
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import com.dev.miasnikoff.feature_tabs.ui.list.mapper.DtoToUiMapper
import com.dev.miasnikoff.feature_tabs.ui.list.model.PagedListState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel @Inject constructor(
    private val interactor: ListInteractor,
    private val dtoToUiMapper: DtoToUiMapper,
    private val router: FlowRouter,
    private val eventBus: EventBus,
    private val query: String?,
    private val category: Category?
) : ViewModel() {

    private var job: Job? = null

    private val _liveData: MutableLiveData<PagedListState> = MutableLiveData()
    val liveData: LiveData<PagedListState> get() = _liveData

    private var currentList: MutableList<RecyclerItem> = mutableListOf()
    private var startIndex: Int = DEFAULT_START_INDEX
    private var currentQuery: String = ""
    private var filter: String? = null
    private var orderBy: String? = null

    init {
        getData()
        handleAppEvents()
    }

    private fun handleAppEvents() {
        viewModelScope.launch {
            eventBus.events.collectLatest { event ->
                when(event) {
                    is AppEvent.FavoriteUpdate -> updateBookList()
                    else -> {}
                }
            }
        }
    }

    fun getData() {
        if (query.isNullOrEmpty()) {
            when (category) {
                Category.NEWEST -> {
                    orderBy = OrderBy.NEWEST.type
                    currentQuery = QueryFields.IN_TITLE.type
                }
                Category.FREE -> {
                    filter = Filter.FREE_BOOKS.type
                    orderBy = OrderBy.NEWEST.type
                    currentQuery = QueryFields.IN_TITLE.type
                }
                else -> {}
            }
        } else {
            currentQuery = query
        }
        loadInitialPage()
    }

    fun getData(query: String) {
        currentQuery = query
        loadInitialPage()
    }

    private fun loadInitialPage() {
        _liveData.value = PagedListState.Loading
        currentList.clear()
        getBookList()
    }

    fun loadNextPage() {
        _liveData.value = PagedListState.MoreLoading
        getBookList()
    }

    private fun getBookList() {
        if (liveData.value !is PagedListState.Success) {
            job?.cancel()
            job = viewModelScope.launch {
                interactor.getBooksList(
                    query = currentQuery,
                    filter = filter,
                    orderBy = orderBy,
                    startIndex = startIndex,
                    maxResults = DEFAULT_MAX_VALUES
                )
                    .onSuccess { volumeResponse ->
                        volumeResponse.volumes?.let { volumesDTO ->
                            startIndex += DEFAULT_MAX_VALUES
                            val newList = mutableListOf<RecyclerItem>().apply {
                                addAll((currentList + dtoToUiMapper.toItemList(volumesDTO)).distinctBy { it.id })
                            }
                            currentList = newList
                            _liveData.value = if (newList.isEmpty()) {
                                PagedListState.Empty
                            } else {
                                PagedListState.Success(newList, newList.size < volumeResponse.totalItems)
                            }
                        } ?: if (currentList.isEmpty()) {
                            _liveData.value = PagedListState.Empty
                        } else {
                            _liveData.value = PagedListState.Success(currentList, false)
                        }
                    }
                    .onFailure(::postError)
            }
        }
    }

    fun setFavorite(itemId: String?) {
        viewModelScope.launch {
            (liveData.value as? PagedListState.Success)?.let { state ->
                val bookItem = (state.data.firstOrNull { it.id == itemId } as? BookItem)
                bookItem?.let {
                    when (bookItem.isFavorite) {
                        true -> interactor.removeFromFavorite(bookItem)
                        false -> interactor.saveInFavorite(bookItem)
                    }
                    eventBus.emitEvent(AppEvent.FavoriteUpdate(itemId))
                }
            }
        }
    }

    private fun updateBookList() {
        viewModelScope.launch {
        val newList: MutableList<RecyclerItem> = mutableListOf()
        (liveData.value as? PagedListState.Success)?.let { state ->
                _liveData.value = PagedListState.MoreLoading
                val favoriteList = interactor.getFavorite()
                state.data.forEach { book ->
                    val index = favoriteList.indexOfFirst { favorite -> book.id == favorite.id }
                    when {
                        index > -1 && (book as BookItem).isFavorite.not() ->
                            newList.add(book.copy(isFavorite = true, favoriteIcon = R.drawable.ic_bookmark_24))
                        index == -1 && (book as BookItem).isFavorite ->
                            newList.add(book.copy(isFavorite = false, favoriteIcon = R.drawable.ic_bookmark_border_24))
                        else -> newList.add(book)
                    }
                }
            currentList = newList
                _liveData.value = state.copy(data = currentList)
            }
        }
    }

    private fun postError(message: String? = null) {
        _liveData.value = PagedListState.Failure(message ?: DEFAULT_ERROR_MESSAGE)
    }

    fun navigate(direction: NavDirections) {
        router.navigateTo(direction)
    }

    fun back() {
        router.back()
    }

    companion object {
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
    }
}

@Suppress("UNCHECKED_CAST")
class BookListViewModelFactory @AssistedInject constructor(
    private val interactor: ListInteractor,
    private val dtoToUiMapper: DtoToUiMapper,
    private val router: FlowRouter,
    private val eventBus: EventBus,
    @Assisted private val query: String?,
    @Assisted private val category: Category?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        assert(modelClass == BookListViewModel::class.java)
        return BookListViewModel(interactor, dtoToUiMapper, router, eventBus, query, category) as T
    }
}

@AssistedFactory
interface BookListViewModelAssistedFactory {
    fun create(
        @Assisted query: String?,
        @Assisted category: Category?
    ): BookListViewModelFactory
}
