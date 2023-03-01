package com.dev.miasnikoff.feature_tabs.data.remote

import com.dev.miasnikoff.feature_tabs.data.remote.model.BookDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeResponse
import com.dev.miasnikoff.feature_tabs.domain.RemoteRepository
import com.dev.miasnikoff.network.calladapter.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RemoteRepository {

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

    override suspend fun getVolume(id: String): ApiResponse<BookDTO> {
        return withContext(Dispatchers.IO) {
            apiService.getVolume(volumeId = id)
        }
    }
}