package com.dev.miasnikoff.bookworm.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.domain.DetailsInteractor
import com.dev.miasnikoff.bookworm.ui.details.model.DetailsState
import kotlinx.coroutines.*

class VolumeDetailsViewModel(
    private val interactor: DetailsInteractor = DetailsInteractor(),
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _liveData.value = DetailsState.Failure(throwable.message ?: DEFAULT_ERROR_MESSAGE)
    }
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob() + exceptionHandler)

    private var _liveData: MutableLiveData<DetailsState> = MutableLiveData()
    val liveData: LiveData<DetailsState> get() = _liveData

    fun getDetails(bookId: String) {
        _liveData.value = DetailsState.Loading
        scope.launch {
            _liveData.value = DetailsState.Success(interactor.getDetails(bookId))
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
    }
}