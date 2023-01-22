package com.dev.miasnikoff.bookworm.data.remote

import com.dev.miasnikoff.bookworm.data.remote.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.remote.model.VolumeResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/books/v1/volumes")
    fun getVolumesAsync(
        @Query("q") query: String,
        @Query("filter") filter: String?,
        @Query("langRestrict") langCode: String = "ru",
        @Query("printType") printType: String?,
        @Query("orderBy") orderBy: String?,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int
    ): Deferred<VolumeResponse>

    @GET("/books/v1/volumes/{volumeId}")
    fun getVolumeAsync(
        @Path("volumeId") volumeId: String
    ): Deferred<VolumeDTO>
}