package com.dev.miasnikoff.bookworm.core.network

import com.dev.miasnikoff.bookworm.core.network.model.Volume
import com.dev.miasnikoff.bookworm.core.network.model.VolumeResponse
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourceImpl: RemoteDataSource {

    private val apiService by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    override fun getVolumeList(query: String, startIndex: Int, maxResults: Int, callback: Callback<VolumeResponse>) {
        apiService.getVolumes(query = query, startIndex = startIndex, maxResults = maxResults).enqueue(callback)
    }

    override fun getVolume(id: String, callback: Callback<Volume>) {
        apiService.getVolume(volumeId = id).enqueue(callback)
    }

    companion object {
        private const val BASE_URL = "https://www.googleapis.com"
        const val API_KEY = "AIzaSyBaAqPeUdUOHol2ryiTkz4fEktX1LrFeSg"
    }
}