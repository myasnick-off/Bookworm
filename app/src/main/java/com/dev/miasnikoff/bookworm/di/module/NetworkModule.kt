package com.dev.miasnikoff.bookworm.di.module

import com.dev.miasnikoff.bookworm.BuildConfig
import com.dev.miasnikoff.bookworm.data.AuthService
import com.dev.miasnikoff.bookworm.data.remote.ApiKeyInterceptor
import com.dev.miasnikoff.bookworm.data.remote.ApiResponseCallAdapterFactory
import com.dev.miasnikoff.bookworm.data.remote.ApiService
import com.dev.miasnikoff.bookworm.di.BaseUrl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkProvideModule {

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
        converterFactories: Set<@JvmSuppressWildcards Converter.Factory>,
        callAdapterFactories: Set<@JvmSuppressWildcards CallAdapter.Factory>
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
        converterFactories.forEach(retrofit::addConverterFactory)
        callAdapterFactories.forEach(retrofit::addCallAdapterFactory)
        return retrofit.build()
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return "https://www.googleapis.com"
    }

    @Provides
    @Singleton
    @IntoSet
    fun coroutineCallAdapterFactory(): CallAdapter.Factory {
        return CoroutineCallAdapterFactory()
    }

    @Provides
    @Singleton
    @IntoSet
    fun jsonConverterFactory(): Converter.Factory {
        val json = Json { ignoreUnknownKeys = true }
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun okHttpClient(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_VALUE, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_VALUE, TimeUnit.SECONDS)
        interceptors.forEach(builder::addInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    @IntoSet
    fun loggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }
    }

    companion object {
        private const val CONNECT_TIMEOUT_VALUE = 10L
        private const val READ_TIMEOUT_VALUE = 10L
    }
}

@Module
interface NetworkBindsModule {

    @Binds
    @Singleton
    @IntoSet
    fun apiResponseCallAdapterFactory(factory: ApiResponseCallAdapterFactory): CallAdapter.Factory

    @Binds
    @Singleton
    @IntoSet
    fun apiKeyInterceptor(apiKeyInterceptor: ApiKeyInterceptor): Interceptor
}