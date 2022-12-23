package com.dev.miasnikoff.bookworm.core.network

import com.dev.miasnikoff.bookworm.core.network.model.VolumeDTO
import com.dev.miasnikoff.bookworm.core.network.model.VolumeResponse
import retrofit2.Callback

interface RemoteDataSource {
    fun getVolumeList(query: String, startIndex: Int, maxResults: Int, callback: Callback<VolumeResponse>)
    fun getVolume(id: String, callback: Callback<VolumeDTO>)
}