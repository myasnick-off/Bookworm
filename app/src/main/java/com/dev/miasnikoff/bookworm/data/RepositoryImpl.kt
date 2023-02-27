package com.dev.miasnikoff.bookworm.data

import com.dev.miasnikoff.bookworm.data.remote.ApiService
import com.dev.miasnikoff.bookworm.data.remote.RemoteDataSource
import com.dev.miasnikoff.bookworm.data.remote.model.ApiResponse
import com.dev.miasnikoff.bookworm.data.remote.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.remote.model.VolumeResponse
import com.dev.miasnikoff.bookworm.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val apiService: ApiService = RemoteDataSource.apiService
) : Repository {

    override suspend fun getVolumeList(
        query: String,
        filter: String?,
        printType: String?,
        orderBy: String?,
        startIndex: Int,
        maxResults: Int
    ): ApiResponse<VolumeResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getVolumes(
                query = query,
                filter = filter,
                printType = printType,
                orderBy = orderBy,
                startIndex = startIndex,
                maxResults = maxResults
            )
        }
    }

    override suspend fun getVolume(id: String): ApiResponse<VolumeDTO> {
        return withContext(Dispatchers.IO) {
            apiService.getVolume(volumeId = id)
        }
    }
}