package com.dev.miasnikoff.bookworm.data

import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/books/v1/volumes")
    fun getVolumesAsync(
        @Query("q") query: String,
        @Query("filter") filter: String? = null,
        @Query("langRestrict") langCode: String? = null,
        @Query("printType") printType: String? = null,
        @Query("orderBy") orderBy: String? = null,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int,
        @Query("key") apiKey: String = RemoteDataSource.API_KEY
    ): Deferred<VolumeResponse>

    @GET("/books/v1/volumes/{volumeId}")
    fun getVolumeAsync(
        @Path("volumeId") volumeId: String,
        @Query("key") apiKey: String = RemoteDataSource.API_KEY
    ): Deferred<VolumeDTO>
}