package com.dev.miasnikoff.feature_auth.di.module

import com.dev.miasnikoff.feature_auth.data.AuthService
import dagger.Module
import dagger.Provides

@Module
class AuthNetworkModule {

    @Provides
    fun authService(): AuthService {
        return AuthService()
    }
}