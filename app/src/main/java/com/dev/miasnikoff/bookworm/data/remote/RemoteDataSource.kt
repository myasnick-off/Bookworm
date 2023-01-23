package com.dev.miasnikoff.bookworm.data.remote

import com.dev.miasnikoff.bookworm.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RemoteDataSource {

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    private val json = Json { ignoreUnknownKeys = true }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthorizationInterceptor())
            .connectTimeout(CONNECT_TIMEOUT_VALUE, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_VALUE, TimeUnit.SECONDS)
            .build()
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    private const val CONNECT_TIMEOUT_VALUE = 10L
    private const val READ_TIMEOUT_VALUE = 10L
    private const val BASE_URL = "https://www.googleapis.com"
}