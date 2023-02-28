package com.dev.miasnikoff.feature_auth.di

import androidx.lifecycle.ViewModel

class AuthFeatureComponentViewModel: ViewModel() {

    val component by lazy {
        DaggerAuthComponent.factory()
            .create(checkNotNull(AuthFeatureComponentExternalDepsProvider.featureExternalDeps))
    }

    override fun onCleared() {
        super.onCleared()
        AuthFeatureComponentExternalDepsProvider.featureExternalDeps = null
    }
}

object AuthFeatureComponentExternalDepsProvider {
    var featureExternalDeps: AuthExternalDependencies? = null
}