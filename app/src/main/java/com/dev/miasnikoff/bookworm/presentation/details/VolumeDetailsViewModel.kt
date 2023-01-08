package com.dev.miasnikoff.bookworm.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.data.RepositoryImpl
import com.dev.miasnikoff.bookworm.domain.Repository
import com.dev.miasnikoff.bookworm.presentation.details.mapper.VolumeDetailsMapper
import com.dev.miasnikoff.bookworm.presentation.details.model.DetailsState
import kotlinx.coroutines.*

class VolumeDetailsViewModel(
    private val repository: Repository = RepositoryImpl(),
    private val mapper: VolumeDetailsMapper = VolumeDetailsMapper(),
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _liveData.value = DetailsState.Failure(throwable.message ?: DEFAULT_ERROR_MESSAGE)
    }
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob() + exceptionHandler)

    private var _liveData: MutableLiveData<DetailsState> = MutableLiveData()
    val liveData: LiveData<DetailsState> get() = _liveData

    fun getDetails(volumeId: String) {
        _liveData.value = DetailsState.Loading
        scope.launch {

            val volumeDTO = repository.getVolume(volumeId)
            _liveData.value = DetailsState.Success(mapper(volumeDTO))
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
    }
}