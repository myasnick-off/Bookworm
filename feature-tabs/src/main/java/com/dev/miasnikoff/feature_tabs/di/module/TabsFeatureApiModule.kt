package com.dev.miasnikoff.feature_tabs.di.module

import com.dev.miasnikoff.feature_tabs.TabsFeatureApiImpl
import com.dev.miasnikoff.feature_tabs_api.TabsFeatureApi
import dagger.Binds
import dagger.Module

@Module
interface TabsFeatureApiModule {

    @Binds
    fun bindTabFeatureApi(api: TabsFeatureApiImpl): TabsFeatureApi
}