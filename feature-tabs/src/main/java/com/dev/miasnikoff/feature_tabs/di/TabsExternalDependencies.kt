package com.dev.miasnikoff.feature_tabs.di

import android.content.Context
import com.dev.miasnikoff.core_di.FeatureExternalDependencies
import com.dev.miasnikoff.core_navigation.router.GlobalRouter
import retrofit2.Retrofit

interface TabsExternalDependencies : FeatureExternalDependencies {
    val context: Context
    val retrofit: Retrofit
    val globalRouter: GlobalRouter
}