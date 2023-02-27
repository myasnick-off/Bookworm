package com.dev.miasnikoff.feature_tabs.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dev.miasnikoff.core.event.AppEvent
import com.dev.miasnikoff.core.event.EventBus
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.BaseViewModel
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractor
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import com.dev.miasnikoff.feature_tabs.ui.list.mapper.EntityToUiMapper
import com.dev.miasnikoff.feature_tabs.ui.list.model.PagedListState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val interactor: ListInteractor,
    private val entityToUiMapper: EntityToUiMapper,
    router: FlowRouter,
    private val eventBus: EventBus,
) : BaseViewModel(router) {

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
                    is AppEvent.FavoriteUpdate, is AppEvent.HistoryUpdate -> getAllHistory()
                    else -> {}
                }
            }
        }
    }

    fun getInitialPage() {
        _liveData.value = PagedListState.Loading
        currentList.clear()
        getAllHistory()
    }

    private fun getAllHistory() {
        viewModelScope.launch {
            val newList = mutableListOf<RecyclerItem>().apply {
                addAll(entityToUiMapper.toItemList(interactor.getHistory()))
                sortBy { it.id }
            }
            currentList = newList
            _liveData.value = if (currentList.isEmpty()) {
                PagedListState.Empty
            } else {
                PagedListState.Success(currentList, false)
            }
        }
    }

    fun setFavorite(itemId: String) {
        viewModelScope.launch {
            _liveData.value = PagedListState.MoreLoading
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

    fun removeAllHistory() {
        viewModelScope.launch {
            interactor.removeAllHistory()
            eventBus.emitEvent(AppEvent.HistoryUpdate())
        }
    }

    fun removeFromHistory(itemId: String) {
        viewModelScope.launch {
            _liveData.value = PagedListState.MoreLoading
            val index = currentList.indexOfFirst { it.id == itemId }
            val bookItem = (currentList.firstOrNull { it.id == itemId } as? BookItem)
            if (index > -1 && bookItem != null) {
                interactor.removeFromHistory(bookItem)
                eventBus.emitEvent(AppEvent.HistoryUpdate(itemId))
            }
        }
    }
}