package com.dev.miasnikoff.core_di.annotations

import androidx.lifecycle.ViewModel
import com.dev.miasnikoff.core_di.FeatureExternalDependencies
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FeatureExternalDepsKey(val value: KClass<out FeatureExternalDependencies>)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
