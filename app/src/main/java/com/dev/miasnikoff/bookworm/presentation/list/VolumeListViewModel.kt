package com.dev.miasnikoff.bookworm.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.data.RepositoryImpl
import com.dev.miasnikoff.bookworm.domain.Repository
import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.presentation.list.mapper.VolumeDataMapper
import com.dev.miasnikoff.bookworm.presentation.list.model.VolumeItem
import com.dev.miasnikoff.bookworm.presentation.list.model.VolumeListState
import kotlinx.coroutines.*

class VolumeListViewModel(
    private val dataSource: Repository = RepositoryImpl(),
    private val mapper: VolumeDataMapper = VolumeDataMapper()
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _liveData.value = VolumeListState.Failure(throwable.message ?: DEFAULT_ERROR_MESSAGE)
    }
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob() + exceptionHandler)
    private var job: Job? = null

    private val _liveData: MutableLiveData<VolumeListState> = MutableLiveData()
    val liveData: LiveData<VolumeListState> get() = _liveData

    private var currentList: MutableList<RecyclerItem> = mutableListOf()

    fun getInitialPage(query: String) {
        getVolumeList(query, DEFAULT_START_INDEX)
    }

    fun loadNextPage(query: String) {
        _liveData.value = VolumeListState.MoreLoading
        getVolumeList(query, currentList.size)
    }

    private fun getVolumeList(query: String, startIndex: Int) {
        if (liveData.value !is VolumeListState.Success) {
            if (startIndex == DEFAULT_START_INDEX) {
                _liveData.value = VolumeListState.Loading
            }
            job?.cancel()
            job = scope.launch {
                val volumeResponse = dataSource.getVolumeList(
                    query = query,
                    startIndex = startIndex,
                    maxResults = DEFAULT_MAX_VALUES
                )
                val newList = mutableListOf<RecyclerItem>().apply {
                    addAll(currentList + mapper.toRecyclerItems(volumeResponse.volumes))
                }
                currentList = newList
                _liveData.value =
                    VolumeListState.Success(currentList, newList.size < volumeResponse.totalItems)
            }
        }
    }


    fun setFavorite(itemId: String) {
        val newList: MutableList<RecyclerItem> = mutableListOf()
        val currentState = liveData.value as? VolumeListState.Success
        currentState?.let { state ->
            newList.addAll(state.data)
            val index = newList.indexOfFirst { it.id == itemId }
            val volumeItem = (newList.firstOrNull { it.id == itemId } as? VolumeItem)
            if (index > -1 && volumeItem != null) {
                newList[index] = if (volumeItem.isFavorite)
                    volumeItem.copy(isFavorite = false, favoriteIcon = R.drawable.ic_bookmark_border_24)
                else
                    volumeItem.copy(isFavorite = true, favoriteIcon = R.drawable.ic_bookmark_24)
            }
            currentList = newList
            _liveData.value = state.copy(data = currentList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    companion object {
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error!"
    }
}