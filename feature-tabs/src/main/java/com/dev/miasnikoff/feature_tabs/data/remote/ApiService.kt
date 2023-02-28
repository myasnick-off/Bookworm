package com.dev.miasnikoff.feature_tabs.data.remote

import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeResponse
import com.dev.miasnikoff.network.calladapter.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/books/v1/volumes")
    suspend fun getVolumes(
        @Query("q") query: String,
        @Query("filter") filter: String?,
        @Query("langRestrict") langCode: String = "ru",
        @Query("printType") printType: String?,
        @Query("orderBy") orderBy: String?,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int
    ): ApiResponse<VolumeResponse>

    @GET("/books/v1/volumes/{volumeId}")
    suspend fun getVolume(
        @Path("volumeId") volumeId: String
    ): ApiResponse<VolumeDTO>
}