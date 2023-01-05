package com.dev.miasnikoff.bookworm.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.bookworm.domain.Repository
import com.dev.miasnikoff.bookworm.data.RepositoryImpl
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.presentation.details.mapper.VolumeDetailsMapper
import com.dev.miasnikoff.bookworm.presentation.details.model.DetailsState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VolumeDetailsViewModel(
    private val repository: Repository = RepositoryImpl(),
    private val mapper: VolumeDetailsMapper = VolumeDetailsMapper(),
) : ViewModel() {

    private var _liveData: MutableLiveData<DetailsState> = MutableLiveData()
    val liveData: LiveData<DetailsState> get() = _liveData

    fun getDetails(volumeId: String) {
        _liveData.value = DetailsState.Loading
        repository.getVolume(volumeId, object : Callback<VolumeDTO> {
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