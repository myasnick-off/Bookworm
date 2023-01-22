package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.data.remote.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.remote.model.VolumeResponse
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

    companion object {
        private const val DEFAULT_START_INDEX = 0
        private const val DEFAULT_MAX_VALUES = 20
    }
}