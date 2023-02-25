package com.dev.miasnikoff.feature_tabs.di.module

import com.dev.miasnikoff.feature_tabs.data.remote.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object TabsNetworkModule {

    @Provides
    fun apiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}