package com.dev.miasnikoff.bookworm.data

import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeResponse
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
    ): VolumeResponse {
        return withContext(Dispatchers.IO) {
            apiService.getVolumesAsync(
                query = query,
                filter = filter,
                printType = printType,
                orderBy = orderBy,
                startIndex = startIndex,
                maxResults = maxResults
            ).await()
        }
    }

    override suspend fun getVolume(id: String): VolumeDTO {
        return withContext(Dispatchers.IO) {
            apiService.getVolumeAsync(volumeId = id).await()
        }
    }

    override suspend fun getLastSeenVolumes(): List<VolumeDTO> {
        TODO("Not yet implemented")
    }
}