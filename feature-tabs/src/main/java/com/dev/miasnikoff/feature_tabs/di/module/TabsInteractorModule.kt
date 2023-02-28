package com.dev.miasnikoff.feature_tabs.di.module

import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractor
import com.dev.miasnikoff.feature_tabs.domain.interactor.ListInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface TabsInteractorModule {

    @Binds
    fun bindListInteractor(impl: ListInteractorImpl): ListInteractor
}