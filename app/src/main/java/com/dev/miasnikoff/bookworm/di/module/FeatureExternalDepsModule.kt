package com.dev.miasnikoff.bookworm.di.module

import com.dev.miasnikoff.bookworm.di.AppComponent
import com.dev.miasnikoff.core_di.FeatureExternalDependencies
import com.dev.miasnikoff.core_di.annotations.FeatureExternalDepsKey
import com.dev.miasnikoff.feature_auth.di.AuthExternalDependencies
import com.dev.miasnikoff.feature_tabs.di.TabsExternalDependencies
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FeatureExternalDepsModule {

    @Binds
    @IntoMap
    @FeatureExternalDepsKey(TabsExternalDependencies::class)
    fun bindTabsExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies

    @Binds
    @IntoMap
    @FeatureExternalDepsKey(AuthExternalDependencies::class)
    fun bindAuthExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies

    // todo: add SplashExternalDependencies
}