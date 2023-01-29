package com.dev.miasnikoff.bookworm.di.module

import com.dev.miasnikoff.bookworm.BuildConfig
import com.dev.miasnikoff.bookworm.data.AuthService
import com.dev.miasnikoff.bookworm.data.remote.ApiKeyInterceptor
import com.dev.miasnikoff.bookworm.data.remote.ApiResponseCallAdapterFactory
import com.dev.miasnikoff.bookworm.data.remote.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun apiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun authService(): AuthService {
        return AuthService()
    }

    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient,
        jsonConverterFactory: Converter.Factory,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
        apiResponseCallAdapterFactory: ApiResponseCallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .addCallAdapterFactory(apiResponseCallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun coroutineCallAdapterFactory(): CoroutineCallAdapterFactory {
        return CoroutineCallAdapterFactory()
    }

    @Provides
    fun apiResponseCallAdapterFactory(): ApiResponseCallAdapterFactory {
        return ApiResponseCallAdapterFactory()
    }

    @Provides
    fun jsonConverterFactory(): Converter.Factory {
        val json = Json { ignoreUnknownKeys = true }
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(CONNECT_TIMEOUT_VALUE, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_VALUE, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    fun apiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor()
    }

    private val CONNECT_TIMEOUT_VALUE = 10L
    private val READ_TIMEOUT_VALUE = 10L
    private val BASE_URL = "https://www.googleapis.com"
}