package com.dev.miasnikoff.network.di

import com.dev.miasnikoff.network.calladapter.ApiResponseCallAdapterFactory
import com.dev.miasnikoff.network.interceptor.ApiKeyInterceptor
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import retrofit2.CallAdapter

@Module
interface CoreNetworkModule {

    @Binds
    @IntoSet
    fun apiResponseCallAdapterFactory(factory: ApiResponseCallAdapterFactory): CallAdapter.Factory

    @Binds
    @IntoSet
    fun apiKeyInterceptor(apiKeyInterceptor: ApiKeyInterceptor): Interceptor
}