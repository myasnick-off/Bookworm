package com.dev.miasnikoff.feature_tabs.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dev.miasnikoff.core.event.AppEvent
import com.dev.miasnikoff.core.event.EventBus
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractor
import com.dev.miasnikoff.feature_tabs.domain.model.*
import com.dev.miasnikoff.feature_tabs.ui.base.BaseListViewModel
import com.dev.miasnikoff.feature_tabs.ui.base.ListState
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import com.dev.miasnikoff.feature_tabs.ui.list.mapper.DtoToUiMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel @Inject constructor(
    private val interactor: ListInteractor,
    private val dtoToUiMapper: DtoToUiMapper,
    router: FlowRouter,
    private val eventBus: EventBus,
    private val query: String?,
    private val category: Category?
) : BaseListViewModel(router) {

    private var currentList: MutableList<RecyclerItem> = mutableListOf()
    private var startIndex: Int = DEFAULT_START_INDEX
    private var currentQuery: String = ""
    private var filter: String? = null
    private var orderBy: String? = null

    init {
        getInitialData()
        handleAppEvents()
    }

    private fun handleAppEvents() {
        viewModelScope.launch {
            eventBus.events.collectLatest { event ->
                when (event) {
                    is AppEvent.FavoriteUpdate -> updateBookList()
                    else -> {}
                }
            }
        }
    }

    override fun getInitialData() {
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

    fun getDataByQuery(query: String) {
        currentQuery = query
        loadInitialPage()
    }

    private fun loadInitialPage() {
        mScreenState.value = ListState.EmptyLoading
        startIndex = DEFAULT_START_INDEX
        currentList.clear()
        getBookList()
    }

    fun loadNextPage() {
        mScreenState.value = ListState.Loading
        getBookList()
    }

    private fun getBookList() {
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
                    mScreenState.value = volumeResponse.volumes?.let { booksDTO ->
                        startIndex += DEFAULT_MAX_VALUES
                        val newList = mutableListOf<RecyclerItem>().apply {
                            addAll((currentList + dtoToUiMapper.toItemList(booksDTO)).distinctBy { it.id })
                        }
                        currentList = newList
                        if (newList.isEmpty()) ListState.Empty
                        else ListState.Success(newList, newList.size < volumeResponse.totalItems)
                    } ?: if (currentList.isEmpty()) ListState.Empty
                    else ListState.Success(currentList, false)
                }
                .onFailure(::postError)
        }
    }

    fun setFavorite(itemId: String?) {
        viewModelScope.launch {
            (screenState.value as? ListState.Success)?.let { state ->
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
        (screenState.value as? ListState.Success)?.let { state ->
            mScreenState.value = ListState.Loading
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
            mScreenState.value = state.copy(data = currentList)
            }
        }
    }

    companion object {
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
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
