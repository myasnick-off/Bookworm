package com.dev.miasnikoff.feature_tabs.ui.list

import androidx.lifecycle.viewModelScope
import com.dev.miasnikoff.core.event.AppEvent
import com.dev.miasnikoff.core.event.EventBus
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractor
import com.dev.miasnikoff.feature_tabs.ui.base.BaseListViewModel
import com.dev.miasnikoff.feature_tabs.ui.base.ListState
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import com.dev.miasnikoff.feature_tabs.ui.list.mapper.EntityToUiMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val interactor: ListInteractor,
    private val entityToUiMapper: EntityToUiMapper,
    router: FlowRouter,
    private val eventBus: EventBus,
) : BaseListViewModel(router) {

    private var currentList: MutableList<RecyclerItem> = mutableListOf()

    init {
        getInitialData()
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

    override fun getInitialData() {
        mutableStateFlow.value = ListState.EmptyLoading
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
            mutableStateFlow.value = if (currentList.isEmpty()) {
                ListState.Empty
            } else {
                ListState.Success(currentList, false)
            }
        }
    }

    fun setFavorite(itemId: String) {
        viewModelScope.launch {
            mutableStateFlow.value = ListState.Loading
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
            mutableStateFlow.value = ListState.Loading
            val index = currentList.indexOfFirst { it.id == itemId }
            val bookItem = (currentList.firstOrNull { it.id == itemId } as? BookItem)
            if (index > -1 && bookItem != null) {
                interactor.removeFromHistory(bookItem)
                eventBus.emitEvent(AppEvent.HistoryUpdate(itemId))
            }
        }
    }
}