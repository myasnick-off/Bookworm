package com.dev.miasnikoff.bookworm.di.module

import com.dev.miasnikoff.bookworm.data.AuthRepositoryImpl
import com.dev.miasnikoff.bookworm.data.RemoteRepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.LocalRepositoryImpl
import com.dev.miasnikoff.bookworm.domain.AuthRepository
import com.dev.miasnikoff.bookworm.domain.LocalRepository
import com.dev.miasnikoff.bookworm.domain.RemoteRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindRemoteRepository(impl: RemoteRepositoryImpl): RemoteRepository

    @Binds
    @Singleton
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindLocalRepository(impl: LocalRepositoryImpl): LocalRepository
}