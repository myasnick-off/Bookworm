package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeResponse
import com.dev.miasnikoff.bookworm.domain.model.OrderBy
import com.dev.miasnikoff.bookworm.domain.model.PrintType

interface Repository {
    suspend fun getVolumeList(
        query: String,
        filter: String? = null,
        printType: String? = PrintType.BOOKS.type,
        orderBy: String? = OrderBy.RELEVANCE.type,
        startIndex: Int = DEFAULT_START_INDEX,
        maxResults: Int = DEFAULT_MAX_VALUES
    ): VolumeResponse

    suspend fun getVolume(id: String): VolumeDTO
    suspend fun getLastSeenVolumes(): List<VolumeDTO>
    suspend fun getNewestVolumes(): VolumeResponse
    suspend fun getPopularFreeVolumes(): VolumeResponse

    companion object {
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
    }
}