package com.dev.miasnikoff.core.di

import com.dev.miasnikoff.core.event.EventBus
import com.dev.miasnikoff.core.event.EventBusImpl
import com.dev.miasnikoff.core_di.annotations.AppScope
import dagger.Binds
import dagger.Module

@Module
interface CoreBindModule {

    @AppScope
    @Binds
    fun bindEventBus(impl: EventBusImpl): EventBus
}