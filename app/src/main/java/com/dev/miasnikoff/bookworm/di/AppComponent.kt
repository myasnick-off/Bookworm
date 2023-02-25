package com.dev.miasnikoff.bookworm.di

import android.content.Context
import com.dev.miasnikoff.bookworm.MainActivity
import com.dev.miasnikoff.bookworm.di.module.AppModule
import com.dev.miasnikoff.bookworm.di.module.AppNetworkModule
import com.dev.miasnikoff.bookworm.di.module.FeatureExternalDepsModule
import com.dev.miasnikoff.core.di.CoreBindModule
import com.dev.miasnikoff.core_di.annotations.AppScope
import com.dev.miasnikoff.core_navigation.di.GlobalNavigationModule
import com.dev.miasnikoff.feature_auth.di.AuthExternalDependencies
import com.dev.miasnikoff.feature_tabs.di.TabsExternalDependencies
import com.dev.miasnikoff.network.di.CoreNetworkModule
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        AppModule::class,
        FeatureExternalDepsModule::class,
        GlobalNavigationModule::class,
        CoreNetworkModule::class,
        AppNetworkModule::class,
        CoreBindModule::class
    ]
)

interface AppComponent : TabsExternalDependencies, AuthExternalDependencies {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}