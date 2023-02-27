package com.dev.miasnikoff.feature_tabs.ui.list

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val interactor: ListInteractor,
    private val entityToUiMapper: EntityToUiMapper,
    router: FlowRouter,
    private val eventBus: EventBus
) : BaseViewModel(router) {

    private val mutableStateFlow = MutableStateFlow<PagedListState>(PagedListState.Empty)
    val stateFlow = mutableStateFlow.asStateFlow()

    private var currentList: MutableList<RecyclerItem> = mutableListOf()

    init {
        getInitialPage()
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

    fun getInitialPage() {
        mutableStateFlow.value = PagedListState.Loading
        currentList.clear()
        getAllFavorite()
    }

    private fun getAllFavorite() {
        viewModelScope.launch {
            val newList = mutableListOf<RecyclerItem>().apply {
                addAll(entityToUiMapper.toItemList(interactor.getFavorite()))
            }
            currentList = newList
            mutableStateFlow.value =
                if (currentList.isEmpty()) PagedListState.Empty
                else PagedListState.Success(currentList, false)
        }
    }

    fun removeFromFavorite(itemId: String) {
        mutableStateFlow.value = PagedListState.MoreLoading
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