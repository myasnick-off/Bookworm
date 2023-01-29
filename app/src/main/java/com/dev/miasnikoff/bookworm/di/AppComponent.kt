package com.dev.miasnikoff.bookworm.di

import com.dev.miasnikoff.bookworm.di.module.*
import dagger.Component

@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DBModule::class,
        RepositoryModule::class,
        InteractorModule::class
    ]
)
interface AppComponent {
}