package com.dev.miasnikoff.feature_auth.di.module

import com.dev.miasnikoff.feature_auth.data.AuthRepositoryImpl
import com.dev.miasnikoff.feature_auth.domain.AuthRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthRepositoryModule {

    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}