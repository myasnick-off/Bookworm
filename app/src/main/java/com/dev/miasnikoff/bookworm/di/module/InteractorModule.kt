package com.dev.miasnikoff.bookworm.di.module

import com.dev.miasnikoff.bookworm.data.RemoteRepositoryImpl
import com.dev.miasnikoff.bookworm.data.local.LocalRepositoryImpl
import com.dev.miasnikoff.bookworm.domain.DetailsInteractor
import com.dev.miasnikoff.bookworm.domain.LocalRepository
import com.dev.miasnikoff.bookworm.domain.RemoteRepository
import com.dev.miasnikoff.bookworm.ui.details.mapper.VolumeDetailsMapper
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    /*@Provides
    fun provideDetailsInteractor(remoteRepository: RemoteRepository,localRepository: LocalRepository,mapper: VolumeDetailsMapper): DetailsInteractor {
        return DetailsInteractor(remoteRepository, localRepository, mapper)
    }*/
}