package com.dev.miasnikoff.bookworm.ui.list

import androidx.lifecycle.*
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.domain.ListInteractor
import com.dev.miasnikoff.bookworm.domain.model.*
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookItem
import com.dev.miasnikoff.bookworm.ui.list.mapper.DtoToUiMapper
import com.dev.miasnikoff.bookworm.ui.list.model.PagedListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel(
    private val interactor: ListInteractor,
    private val dtoToUiMapper: DtoToUiMapper,
) : ViewModel() {

    private var job: Job? = null

    private val _liveData: MutableLiveData<PagedListState> = MutableLiveData()
    val liveData: LiveData<PagedListState> get() = _liveData

    private var currentList: MutableList<RecyclerItem> = mutableListOf()
    private var startIndex: Int = DEFAULT_START_INDEX
    private var currentQuery: String = ""
    private var filter: String? = null
    private var orderBy: String? = null

    fun getInitialPage(query: String) {
        currentQuery = query
        _liveData.value = PagedListState.Loading
        currentList.clear()
        getVolumeList()
    }

    fun getInitialPage(category: Category) {
        when (category) {
            Category.NEWEST -> {
                orderBy = OrderBy.NEWEST.type
                getInitialPage(query = QueryFields.IN_TITLE.type)
            }
            Category.FREE -> {
                filter = Filter.FREE_BOOKS.type
                orderBy = OrderBy.NEWEST.type
                getInitialPage(query = QueryFields.IN_TITLE.type)
            }
            else -> {}
        }
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
                        bookItem.copy(isFavorite = false, favoriteIcon = R.drawable.ic_bookmark_border_24)
                    }
                    else {
                        interactor.saveInFavorite(bookItem)
                        bookItem.copy(isFavorite = true, favoriteIcon = R.drawable.ic_bookmark_24)
                    }
                }
                currentList = newList
                _liveData.value = state.copy(data = currentList)
            }
        }
    }

    companion object {
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
        private const val EMPTY_RESULT_MESSAGE = "Nothing found!"
    }
}

@Suppress("UNCHECKED_CAST")
class BookListViewModelProviderFactory @Inject constructor(
    private val interactor: ListInteractor,
    private val dtoToUiMapper: DtoToUiMapper,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        assert(modelClass == BookListViewModel::class.java)
        return BookListViewModel(interactor, dtoToUiMapper) as T
    }
}