package com.dev.miasnikoff.bookworm.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.core.network.RemoteDataSource
import com.dev.miasnikoff.bookworm.core.network.RemoteDataSourceImpl
import com.dev.miasnikoff.bookworm.core.network.model.VolumeDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VolumeDetailsViewModel(
    private val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl(),
    private val mapper: VolumeDetailsMapper = VolumeDetailsMapper(),
) : ViewModel() {

    private var _liveData: MutableLiveData<DetailsState> = MutableLiveData()
    val liveData: LiveData<DetailsState> get() = _liveData

    fun getDetails(volumeId: String) {
        _liveData.value = DetailsState.Loading
        remoteDataSource.getVolume(volumeId, object : Callback<VolumeDTO> {
            override fun onResponse(call: Call<VolumeDTO>, response: Response<VolumeDTO>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    _liveData.postValue(DetailsState.Success(mapper(body)))
                } else {
                    _liveData.postValue(DetailsState.Failure("Data failure!"))
                }
            }

            override fun onFailure(call: Call<VolumeDTO>, t: Throwable) {
                _liveData.postValue(DetailsState.Failure(t.message ?: "Unknown error!"))
            }
        })
    }
}