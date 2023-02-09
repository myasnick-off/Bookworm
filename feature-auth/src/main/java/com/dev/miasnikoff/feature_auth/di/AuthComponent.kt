package com.dev.miasnikoff.feature_auth.di

import com.dev.miasnikoff.core_di.annotations.FeatureScope
import com.dev.miasnikoff.core_navigation.di.FlowNavigationModule
import com.dev.miasnikoff.feature_auth.di.module.AuthNetworkModule
import com.dev.miasnikoff.feature_auth.di.module.AuthRepositoryModule
import com.dev.miasnikoff.feature_auth.di.module.AuthViewModelModule
import com.dev.miasnikoff.feature_auth.ui.LoginFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [
        AuthNetworkModule::class,
        AuthRepositoryModule::class,
        AuthViewModelModule::class,
        FlowNavigationModule::class
    ],
    dependencies = [AuthExternalDependencies::class]
)
interface AuthComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: AuthExternalDependencies): AuthComponent
    }

    fun inject(loginFragment: LoginFragment)
}