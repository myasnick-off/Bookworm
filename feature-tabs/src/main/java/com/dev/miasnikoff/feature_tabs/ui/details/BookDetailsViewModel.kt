package com.dev.miasnikoff.feature_tabs.ui.details

import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.feature_tabs.domain.interactor.DetailsInteractor
import com.dev.miasnikoff.feature_tabs.domain.model.onFailure
import com.dev.miasnikoff.feature_tabs.domain.model.onSuccess
import com.dev.miasnikoff.feature_tabs.ui.details.mapper.BookDetailsToListMapper
import com.dev.miasnikoff.feature_tabs.ui.details.model.DetailsState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class BookDetailsViewModel @AssistedInject constructor(
    private val interactor: DetailsInteractor,
    private val router: FlowRouter,
    private val mapper: BookDetailsToListMapper,
    private val bookId: String
) : ViewModel() {

    private var _liveData: MutableLiveData<DetailsState> = MutableLiveData()
    val liveData: LiveData<DetailsState> get() = _liveData

    init {
        getDetails()
    }

    fun getDetails() {
        _liveData.value = DetailsState.Loading
        viewModelScope.launch {
            interactor.getDetails(bookId)
                .onSuccess { details ->
                    _liveData.value = DetailsState.Success(mapper.toList(details))
                }
                .onFailure { message ->
                    _liveData.value = DetailsState.Failure(message ?: DEFAULT_ERROR_MESSAGE)
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
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
    }
}

@Suppress("UNCHECKED_CAST")
class BookDetailsViewModelFactory @AssistedInject constructor(
    private val interactor: DetailsInteractor,
    private val router: FlowRouter,
    private val mapper: BookDetailsToListMapper,
    @Assisted private val bookId: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        assert(modelClass == BookDetailsViewModel::class.java)
        return BookDetailsViewModel(interactor, router, mapper, bookId) as T
    }
}

@AssistedFactory
interface BookDetailsViewModelAssistedFactory {
    fun create(@Assisted bookId: String): BookDetailsViewModelFactory
}