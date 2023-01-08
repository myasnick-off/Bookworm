package com.dev.miasnikoff.bookworm.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteDataSource {

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(ApiService::class.java)
    }

    private const val BASE_URL = "https://www.googleapis.com"
    const val API_KEY = "AIzaSyBaAqPeUdUOHol2ryiTkz4fEktX1LrFeSg"
}