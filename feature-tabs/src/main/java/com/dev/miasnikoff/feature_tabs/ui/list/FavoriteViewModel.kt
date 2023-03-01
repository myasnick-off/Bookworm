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

class FavoriteViewModel @Inject constructor(
    private val interactor: ListInteractor,
    private val entityToUiMapper: EntityToUiMapper,
    router: FlowRouter,
    private val eventBus: EventBus
) : BaseListViewModel(router) {

    private var currentList: MutableList<RecyclerItem> = mutableListOf()

    init {
        getInitialData()
        handleAppEvents()
    }

    private fun handleAppEvents() {
        viewModelScope.launch {
            eventBus.events.collectLatest { event ->
                when (event) {
                    is AppEvent.FavoriteUpdate, is AppEvent.HistoryUpdate -> getAllFavorite()
                    else -> {}
                }
            }
        }
    }

    override fun getInitialData() {
        mutableStateFlow.value = ListState.EmptyLoading
        getAllFavorite()
    }

    private fun getAllFavorite() {
        viewModelScope.launch {
            val newList = mutableListOf<RecyclerItem>().apply {
                addAll(entityToUiMapper.toItemList(interactor.getFavorite()))
            }
            currentList = newList
            mutableStateFlow.value =
                if (currentList.isEmpty()) ListState.Empty
                else ListState.Success(currentList, false)
        }
    }

    fun removeFromFavorite(itemId: String) {
        mutableStateFlow.value = ListState.Loading
        viewModelScope.launch {
            val index = currentList.indexOfFirst { it.id == itemId }
            val bookItem = (currentList.firstOrNull { it.id == itemId } as? BookItem)
            if (index > -1 && bookItem != null) {
                interactor.removeFromFavorite(bookItem)
                eventBus.emitEvent(AppEvent.FavoriteUpdate(itemId))
            }
        }
    }

    fun removeAllFavorite() {
        viewModelScope.launch {
            interactor.removeAllFavorite()
            eventBus.emitEvent(AppEvent.FavoriteUpdate())
        }
    }
}