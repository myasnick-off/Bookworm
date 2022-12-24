package com.dev.miasnikoff.bookworm.domain

import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeResponse
import retrofit2.Callback

interface Repository {
    fun getVolumeList(query: String, startIndex: Int, maxResults: Int, callback: Callback<VolumeResponse>)
    fun getVolume(id: String, callback: Callback<VolumeDTO>)
}