package com.dev.miasnikoff.core_navigation.di

import androidx.navigation.NavController
import com.dev.miasnikoff.core_di.annotations.AppScope
import com.dev.miasnikoff.core_di.annotations.GlobalNavHolder
import com.dev.miasnikoff.core_navigation.navigator.GlobalNavigatorHolder
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder
import com.dev.miasnikoff.core_navigation.router.GlobalRouter
import com.dev.miasnikoff.core_navigation.router.GlobalRouterImpl
import dagger.Module
import dagger.Provides

@Module
object GlobalNavigationModule {

    @AppScope
    @Provides
    fun provideGlobalRouter(@GlobalNavHolder navigatorHolder: NavigatorHolder<NavController>): GlobalRouter {
        return GlobalRouterImpl(navigatorHolder)
    }

    @AppScope
    @Provides
    @GlobalNavHolder
    fun provideGlobalNavigatorHolder(): NavigatorHolder<NavController> {
        return GlobalNavigatorHolder()
    }
}