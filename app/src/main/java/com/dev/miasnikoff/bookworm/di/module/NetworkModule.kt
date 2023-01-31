package com.dev.miasnikoff.bookworm.di.module

import com.dev.miasnikoff.bookworm.BuildConfig
import com.dev.miasnikoff.bookworm.data.AuthService
import com.dev.miasnikoff.bookworm.data.remote.ApiKeyInterceptor
import com.dev.miasnikoff.bookworm.data.remote.ApiResponseCallAdapterFactory
import com.dev.miasnikoff.bookworm.data.remote.ApiService
import com.dev.miasnikoff.bookworm.di.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun authService(): AuthService {
        return AuthService()
    }

    @Provides
    @Singleton
    fun retrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        @JsonConverter jsonConverterFactory: Converter.Factory,
        @CoroutineAdapter coroutineCallAdapterFactory: CallAdapter.Factory,
        @ApiResponseAdapter apiResponseCallAdapterFactory: CallAdapter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(jsonConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .addCallAdapterFactory(apiResponseCallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return "https://www.googleapis.com"
    }

    @Provides
    @Singleton
    @CoroutineAdapter
    fun coroutineCallAdapterFactory(): CallAdapter.Factory {
        return CoroutineCallAdapterFactory()
    }

    @Provides
    @Singleton
    @ApiResponseAdapter
    fun apiResponseCallAdapterFactory(): CallAdapter.Factory {
        return ApiResponseCallAdapterFactory()
    }

    @Provides
    @Singleton
    @JsonConverter
    fun jsonConverterFactory(): Converter.Factory {
        val json = Json { ignoreUnknownKeys = true }
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun okHttpClient(
        @LoggingIntercept loggingInterceptor: Interceptor,
        @ApiKeyIntercept apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(CONNECT_TIMEOUT_VALUE, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_VALUE, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @LoggingIntercept
    fun loggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @ApiKeyIntercept
    fun apiKeyInterceptor(): Interceptor {
        return ApiKeyInterceptor()
    }

    companion object {
        private const val CONNECT_TIMEOUT_VALUE = 10L
        private const val READ_TIMEOUT_VALUE = 10L
    }
}