package com.dev.miasnikoff.core_di

import androidx.fragment.app.Fragment

typealias FeatureExternalDependenciesContainer =
        Map<Class<out FeatureExternalDependencies>, @JvmSuppressWildcards FeatureExternalDependencies>

interface FeatureExternalDepsProvider {
    val externalDeps: FeatureExternalDependenciesContainer
}

inline fun <reified T : FeatureExternalDependencies> FeatureExternalDepsProvider.get(): T {
    return externalDeps.getValue(T::class.java) as T
}

inline fun <reified T : FeatureExternalDependencies> Fragment.findFeatureExternalDeps(): T {
    return (requireActivity() as? FeatureExternalDepsProvider)?.get()
        ?: error("Feature External Dependencies not found!")
}