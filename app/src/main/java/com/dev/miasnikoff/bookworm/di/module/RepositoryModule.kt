package com.dev.miasnikoff.bookworm.di.module

import com.dev.miasnikoff.bookworm.data.AuthRepositoryImpl
import com.dev.miasnikoff.bookworm.data.AuthService
import com.dev.miasnikoff.bookworm.data.RemoteRepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.LocalRepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.dao.BookDao
import com.dev.miasnikoff.bookworm.data.remote.ApiService
import com.dev.miasnikoff.bookworm.domain.AuthRepository
import com.dev.miasnikoff.bookworm.domain.LocalRepository
import com.dev.miasnikoff.bookworm.domain.RemoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRemoteRepository(apiService: ApiService): RemoteRepository {
        return RemoteRepositoryImpl(apiService)
    }

    @Provides
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService)
    }

    @Provides
    fun provideLocalRepository(bookDao: BookDao): LocalRepository {
        return LocalRepositoryImpl(bookDao)
    }
}