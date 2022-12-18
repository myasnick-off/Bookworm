package com.dev.miasnikoff.bookworm.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.network.RemoteDataSource
import com.dev.miasnikoff.bookworm.core.network.RemoteDataSourceImpl
import com.dev.miasnikoff.bookworm.core.network.model.VolumeResponse
import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.list.adapter.VolumeItem
import com.dev.miasnikoff.bookworm.list.mapper.VolumeDataMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VolumeListViewModel(
    private val dataSource: RemoteDataSource = RemoteDataSourceImpl(),
    private val mapper: VolumeDataMapper = VolumeDataMapper()
) : ViewModel() {

    private val _liveData: MutableLiveData<VolumeListState> = MutableLiveData()
    val liveData: LiveData<VolumeListState> get() = _liveData

    fun getVolumeList(query: String) {
        if (liveData.value !is VolumeListState.Success) {
            _liveData.value = VolumeListState.Loading
            dataSource.getVolumeList(
                query = query,
                startIndex = DEFAULT_START_INDEX,
                maxResults = DEFAULT_MAX_VALUES,
                object : Callback<VolumeResponse> {
                    override fun onResponse(
                        call: Call<VolumeResponse>,
                        response: Response<VolumeResponse>
                    ) {
                        val body = response.body()
                        if (response.isSuccessful && body != null) {
                            val volumeList = mapper.toRecyclerItems(body.volumes)
                            _liveData.postValue(VolumeListState.Success(volumeList))
                        } else {
                            _liveData.postValue(VolumeListState.Failure("Data failure!"))
                        }
                    }

                    override fun onFailure(call: Call<VolumeResponse>, t: Throwable) {
                        _liveData.postValue(VolumeListState.Failure(t.message ?: "Unknown error!"))
                    }
                })
        }
    }

    fun setFavorite(itemId: String) {
        val newList: MutableList<RecyclerItem> = mutableListOf()
        (liveData.value as? VolumeListState.Success)?.data?.let { newList.addAll(it) }
        val index = newList.indexOfFirst { it.id == itemId }
        val volumeItem = (newList.firstOrNull { it.id == itemId } as? VolumeItem)
        if (index > -1 && volumeItem != null) {
            newList[index] = if (volumeItem.isFavorite)
                volumeItem.copy(isFavorite = false, favoriteIcon = R.drawable.ic_bookmark_border_24)
            else
                volumeItem.copy(isFavorite = true, favoriteIcon = R.drawable.ic_bookmark_24)
        }
        _liveData.value = VolumeListState.Success(newList)
    }

    companion object {
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
    }
}