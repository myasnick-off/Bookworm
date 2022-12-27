package com.dev.miasnikoff.bookworm.data

import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/books/v1/volumes")
    fun getVolumes(
        @Query("q") query: String,
        @Query("filter") filter: String? = null,
        @Query("langRestrict") langCode: String? = null,
        @Query("printType") printType: String? = null,
        @Query("orderBy") orderBy: String? = null,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int,
        @Query("key") apiKey: String = RemoteDataSource.API_KEY
    ): Call<VolumeResponse>

    @GET("/books/v1/volumes/{volumeId}")
    fun getVolume(
        @Path("volumeId") volumeId: String,
        @Query("key") apiKey: String = RemoteDataSource.API_KEY
    ): Call<VolumeDTO>
}