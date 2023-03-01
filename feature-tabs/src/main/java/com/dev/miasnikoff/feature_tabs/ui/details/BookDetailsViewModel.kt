package com.dev.miasnikoff.feature_tabs.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dev.miasnikoff.core.event.AppEvent
import com.dev.miasnikoff.core.event.EventBus
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.feature_tabs.domain.interactor.DetailsInteractor
import com.dev.miasnikoff.feature_tabs.domain.model.onFailure
import com.dev.miasnikoff.feature_tabs.domain.model.onSuccess
import com.dev.miasnikoff.feature_tabs.ui.base.BaseListViewModel
import com.dev.miasnikoff.feature_tabs.ui.base.ListState
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls.BookControlsItem
import com.dev.miasnikoff.feature_tabs.ui.details.mapper.BookDetailsToListMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookDetailsViewModel @AssistedInject constructor(
    private val interactor: DetailsInteractor,
    router: FlowRouter,
    private val mapper: BookDetailsToListMapper,
    private val eventBus: EventBus,
    private val bookId: String
) : BaseListViewModel(router) {

    init {
        getInitialData()
        handleAppEvents()
    }

    private fun handleAppEvents() {
        viewModelScope.launch {
            eventBus.events.collectLatest { event ->
                when(event) {
                    is AppEvent.FavoriteUpdate -> updateDetails(event.bookId)
                    else -> {}
                }
            }
        }
    }
    override fun getInitialData() {
        mScreenState.value = ListState.EmptyLoading
        getDetails()
    }

    private fun getDetails() {
        job?.cancel()
        job = viewModelScope.launch {
            interactor.getDetails(bookId)
                .onSuccess { details ->
                    mScreenState.value = ListState.Success(mapper.toList(details))
                    eventBus.emitEvent(AppEvent.HistoryUpdate(bookId))
                }
                .onFailure {
                    mScreenState.value = ListState.Failure
                }
        }
    }

    fun setFavorite() {
        viewModelScope.launch {
            (screenState.value as? ListState.Success)?.let {
                interactor.checkFavorite(bookId)
                    .onSuccess {
                        eventBus.emitEvent(AppEvent.FavoriteUpdate(bookId))
                    }
                    .onFailure {
                        mScreenState.value = ListState.Failure
                    }
            }
        }
    }

    private fun updateDetails(bookId: String?) {
        if (bookId != null && bookId == this.bookId) {
            viewModelScope.launch {
                interactor.getDetails(bookId)
                    .onSuccess { details ->
                        mScreenState.value = ListState.Success(mapper.toList(details))
                    }
                    .onFailure(::postError)
            }
        }
    }

    fun getBookUrl(): String? {
        return (screenState.value as? ListState.Success)?.let { state ->
            val item = state.data.firstOrNull { it is BookControlsItem } as BookControlsItem?
            item?.previewLink
        }
    }
}

@Suppress("UNCHECKED_CAST")
class BookDetailsViewModelFactory @AssistedInject constructor(
    private val interactor: DetailsInteractor,
    private val router: FlowRouter,
    private val mapper: BookDetailsToListMapper,
    private val eventBus: EventBus,
    @Assisted private val bookId: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        assert(modelClass == BookDetailsViewModel::class.java)
        return BookDetailsViewModel(interactor, router, mapper, eventBus, bookId) as T
    }
}

@AssistedFactory
interface BookDetailsViewModelAssistedFactory {
    fun create(@Assisted bookId: String): BookDetailsViewModelFactory
}