package com.dev.miasnikoff.bookworm.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.miasnikoff.bookworm.domain.DetailsInteractor
import com.dev.miasnikoff.bookworm.domain.model.onFailure
import com.dev.miasnikoff.bookworm.domain.model.onSuccess
import com.dev.miasnikoff.bookworm.ui.details.model.DetailsState
import kotlinx.coroutines.launch

class VolumeDetailsViewModel(
    private val interactor: DetailsInteractor
) : ViewModel() {

    private var _liveData: MutableLiveData<DetailsState> = MutableLiveData()
    val liveData: LiveData<DetailsState> get() = _liveData

    fun getDetails(bookId: String) {
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