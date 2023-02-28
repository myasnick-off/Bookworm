package com.dev.miasnikoff.bookworm.di.module

import com.dev.miasnikoff.feature_auth.di.module.AuthFeatureApiModule
import com.dev.miasnikoff.feature_tabs.di.module.TabsFeatureApiModule
import dagger.Module

@Module
interface AppModule : TabsFeatureApiModule, AuthFeatureApiModule