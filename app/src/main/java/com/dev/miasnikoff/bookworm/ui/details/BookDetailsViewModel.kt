package com.dev.miasnikoff.bookworm.ui.details

import androidx.lifecycle.*
import com.dev.miasnikoff.bookworm.domain.DetailsInteractor
import com.dev.miasnikoff.bookworm.domain.model.onFailure
import com.dev.miasnikoff.bookworm.domain.model.onSuccess
import com.dev.miasnikoff.bookworm.ui.details.model.DetailsState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class BookDetailsViewModel @AssistedInject constructor(
    private val interactor: DetailsInteractor,
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
                .onSuccess { details -> _liveData.value = DetailsState.Success(details) }
                .onFailure { message -> _liveData.value = DetailsState.Failure(message ?: DEFAULT_ERROR_MESSAGE) }
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
    }
}

@Suppress("UNCHECKED_CAST")
class BookDetailsViewModelFactory @AssistedInject constructor(
    private val interactor: DetailsInteractor,
    @Assisted private val bookId: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        assert(modelClass == BookDetailsViewModel::class.java)
        return BookDetailsViewModel(interactor, bookId) as T
    }
}

@AssistedFactory
interface BookDetailsViewModelAssistedFactory {
    fun create(@Assisted bookId: String): BookDetailsViewModelFactory
}