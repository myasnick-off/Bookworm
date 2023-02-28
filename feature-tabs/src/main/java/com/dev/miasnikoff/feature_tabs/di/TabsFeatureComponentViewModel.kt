package com.dev.miasnikoff.feature_tabs.di

import androidx.lifecycle.ViewModel

class TabsFeatureComponentViewModel: ViewModel() {

    val component by lazy {
        DaggerTabsComponent.factory()
            .create(checkNotNull(TabsFeatureComponentExternalDepsProvider.featureExternalDeps))
    }

    override fun onCleared() {
        super.onCleared()
        TabsFeatureComponentExternalDepsProvider.featureExternalDeps = null
    }
}

object TabsFeatureComponentExternalDepsProvider {
    var featureExternalDeps: TabsExternalDependencies? = null
}