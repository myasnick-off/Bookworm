package com.dev.miasnikoff.feature_auth.di

import com.dev.miasnikoff.core_di.FeatureExternalDependencies
import com.dev.miasnikoff.core_navigation.router.GlobalRouter
import retrofit2.Retrofit

interface AuthExternalDependencies : FeatureExternalDependencies {
    val retrofit: Retrofit
    val globalRouter: GlobalRouter
}