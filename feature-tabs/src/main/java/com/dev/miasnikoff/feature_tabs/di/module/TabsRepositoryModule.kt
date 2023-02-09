package com.dev.miasnikoff.feature_tabs.di.module

import com.dev.miasnikoff.feature_tabs.data.local.LocalRepositoryImpl
import com.dev.miasnikoff.feature_tabs.data.remote.RemoteRepositoryImpl
import com.dev.miasnikoff.feature_tabs.domain.LocalRepository
import com.dev.miasnikoff.feature_tabs.domain.RemoteRepository
import dagger.Binds
import dagger.Module

@Module
interface TabsRepositoryModule {

    @Binds
    fun bindRemoteRepository(impl: RemoteRepositoryImpl): RemoteRepository

    @Binds
    fun bindLocalRepository(impl: LocalRepositoryImpl): LocalRepository
}