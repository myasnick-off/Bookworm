package com.dev.miasnikoff.feature_tabs.ui.list

import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
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
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel @Inject constructor(
    private val interactor: ListInteractor,
    private val dtoToUiMapper: DtoToUiMapper,
    private val router: FlowRouter,
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
        getVolumeList()
    }

    fun loadNextPage() {
        _liveData.value = PagedListState.MoreLoading
        getVolumeList()
    }

    private fun getVolumeList() {
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
                        _liveData.value = volumeResponse.volumes?.let { volumesDTO ->
                            startIndex += DEFAULT_MAX_VALUES
                            val newList = mutableListOf<RecyclerItem>().apply {
                                addAll((currentList + dtoToUiMapper.toItemList(volumesDTO)).distinctBy { it.id })
                            }
                            currentList = newList
                            PagedListState.Success(currentList, newList.size < volumeResponse.totalItems)
                        } ?: if (currentList.isEmpty()) {
                            PagedListState.Failure(EMPTY_RESULT_MESSAGE)
                        } else {
                            PagedListState.Success(currentList, false)
                        }
                    }
                    .onFailure { message ->
                        _liveData.value = PagedListState.Failure(message ?: DEFAULT_ERROR_MESSAGE)
                    }

            }
        }
    }

    fun setFavorite(itemId: String) {
        viewModelScope.launch {
            val newList: MutableList<RecyclerItem> = mutableListOf()
            val currentState = liveData.value as? PagedListState.Success
            currentState?.let { state ->
                newList.addAll(state.data)
                val index = newList.indexOfFirst { it.id == itemId }
                val bookItem = (newList.firstOrNull { it.id == itemId } as? BookItem)
                if (index > -1 && bookItem != null) {
                    newList[index] = if (bookItem.isFavorite) {
                        interactor.removeFromFavorite(bookItem)
                        bookItem.copy(
                            isFavorite = false,
                            favoriteIcon = com.dev.miasnikoff.core_ui.R.drawable.ic_bookmark_border_24
                        )
                    } else {
                        interactor.saveInFavorite(bookItem)
                        bookItem.copy(
                            isFavorite = true,
                            favoriteIcon = com.dev.miasnikoff.core_ui.R.drawable.ic_bookmark_24
                        )
                    }
                }
                currentList = newList
                _liveData.value = state.copy(data = currentList)
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
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
        private const val EMPTY_RESULT_MESSAGE = "Nothing found!"
    }
}

@Suppress("UNCHECKED_CAST")
class BookListViewModelFactory @AssistedInject constructor(
    private val interactor: ListInteractor,
    private val dtoToUiMapper: DtoToUiMapper,
    private val router: FlowRouter,
    @Assisted private val query: String?,
    @Assisted private val category: Category?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        assert(modelClass == BookListViewModel::class.java)
        return BookListViewModel(interactor, dtoToUiMapper, router, query, category) as T
    }
}

@AssistedFactory
interface BookListViewModelAssistedFactory {
    fun create(
        @Assisted query: String?,
        @Assisted category: Category?
    ): BookListViewModelFactory
}
