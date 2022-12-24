package com.dev.miasnikoff.bookworm.data

import com.dev.miasnikoff.bookworm.domain.Repository
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeResponse
import retrofit2.Callback

class RepositoryImpl(
    private val apiService: ApiService = RemoteDataSource.apiService
) : Repository {

    override fun getVolumeList(
        query: String,
        startIndex: Int,
        maxResults: Int,
        callback: Callback<VolumeResponse>
    ) {
        apiService.getVolumes(query = query, startIndex = startIndex, maxResults = maxResults)
            .enqueue(callback)
    }

    override fun getVolume(id: String, callback: Callback<VolumeDTO>) {
        apiService.getVolume(volumeId = id).enqueue(callback)
    }
}