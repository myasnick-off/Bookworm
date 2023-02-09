package com.dev.miasnikoff.core_navigation.di

import androidx.navigation.NavController
import com.dev.miasnikoff.core_di.annotations.FeatureScope
import com.dev.miasnikoff.core_di.annotations.FlowNavHolder
import com.dev.miasnikoff.core_navigation.navigator.FlowNavigatorHolder
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_navigation.router.FlowRouterImpl
import com.dev.miasnikoff.core_navigation.router.GlobalRouter
import dagger.Module
import dagger.Provides


@Module
class FlowNavigationModule {

    @FeatureScope
    @Provides
    fun provideFlowRouter(
        globalRouter: GlobalRouter,
        @FlowNavHolder navigatorHolder: NavigatorHolder<NavController>
    ): FlowRouter {
        return FlowRouterImpl(globalRouter, navigatorHolder)
    }

    @FeatureScope
    @Provides
    @FlowNavHolder
    fun provideFlowNavigatorHolder(): NavigatorHolder<NavController> {
        return FlowNavigatorHolder()
    }
}
