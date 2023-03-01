package com.dev.miasnikoff.feature_tabs.domain

import com.dev.miasnikoff.feature_tabs.data.remote.model.BookDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeResponse
import com.dev.miasnikoff.feature_tabs.domain.model.OrderBy
import com.dev.miasnikoff.feature_tabs.domain.model.PrintType
import com.dev.miasnikoff.network.calladapter.ApiResponse

interface RemoteRepository {
    suspend fun getVolumeList(
        query: String,
        filter: String? = null,
        printType: String? = PrintType.BOOKS.type,
        orderBy: String? = OrderBy.RELEVANCE.type,
        startIndex: Int = DEFAULT_START_INDEX,
        maxResults: Int = DEFAULT_MAX_VALUES
    ): ApiResponse<VolumeResponse>

    suspend fun getVolume(id: String): ApiResponse<BookDTO>

    companion object {
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
    }
}