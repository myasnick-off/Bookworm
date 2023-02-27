package com.dev.miasnikoff.feature_tabs.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dev.miasnikoff.core.event.AppEvent
import com.dev.miasnikoff.core.event.EventBus
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.BaseViewModel
import com.dev.miasnikoff.feature_tabs.domain.interactor.DetailsInteractor
import com.dev.miasnikoff.feature_tabs.domain.model.onFailure
import com.dev.miasnikoff.feature_tabs.domain.model.onSuccess
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls.BookControlsItem
import com.dev.miasnikoff.feature_tabs.ui.details.mapper.BookDetailsToListMapper
import com.dev.miasnikoff.feature_tabs.ui.details.model.DetailsState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookDetailsViewModel @AssistedInject constructor(
    private val interactor: DetailsInteractor,
    router: FlowRouter,
    private val mapper: BookDetailsToListMapper,
    private val eventBus: EventBus,
    private val bookId: String
) : BaseViewModel(router) {

    private val mutableStateFlow = MutableStateFlow<DetailsState>(DetailsState.Empty)
    val stateFlow = mutableStateFlow.asStateFlow()

    init {
        getDetails()
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

    fun getDetails() {
        mutableStateFlow.value = DetailsState.Loading
        viewModelScope.launch {
            interactor.getDetails(bookId)
                .onSuccess { details ->
                    mutableStateFlow.value = DetailsState.Success(mapper.toList(details))
                    eventBus.emitEvent(AppEvent.HistoryUpdate(bookId))
                }
                .onFailure { message ->
                    mutableStateFlow.value = DetailsState.Failure(message ?: DEFAULT_ERROR_MESSAGE)
                }
        }
    }

    fun setFavorite() {
        viewModelScope.launch {
            (stateFlow.value as? DetailsState.Success)?.let {
                interactor.checkFavorite(bookId)
                    .onSuccess {
                        eventBus.emitEvent(AppEvent.FavoriteUpdate(bookId))
                    }
                    .onFailure { message ->
                        mutableStateFlow.value = DetailsState.Failure(message ?: DEFAULT_ERROR_MESSAGE)
                    }
            }
        }
    }

    private fun updateDetails(bookId: String?) {
        if (bookId != null && bookId == this.bookId) {
            viewModelScope.launch {
                interactor.getDetails(bookId)
                    .onSuccess { details ->
                        mutableStateFlow.value = DetailsState.Success(mapper.toList(details))
                    }
                    .onFailure { message ->
                        mutableStateFlow.value = DetailsState.Failure(message ?: DEFAULT_ERROR_MESSAGE)
                    }
            }
        }
    }

    fun getBookUrl(): String? {
        return (stateFlow.value as? DetailsState.Success)?.let { state ->
            val item = state.data.firstOrNull { it is BookControlsItem } as BookControlsItem?
            item?.previewLink
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
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