package com.dev.miasnikoff.feature_auth.di.module

import com.dev.miasnikoff.feature_auth.AuthFeatureApiImpl
import com.dev.miasnikoff.feature_auth_api.AuthFeatureApi
import dagger.Binds
import dagger.Module

@Module
interface AuthFeatureApiModule {

    @Binds
    fun bindAuthFeatureApi(api: AuthFeatureApiImpl): AuthFeatureApi
}