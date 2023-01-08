package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeResponse

interface Repository {
    suspend fun getVolumeList(query: String, startIndex: Int, maxResults: Int): VolumeResponse
    suspend fun getVolume(id: String): VolumeDTO
}