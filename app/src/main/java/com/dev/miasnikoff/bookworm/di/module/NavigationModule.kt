package com.dev.miasnikoff.bookworm.di.module

import androidx.navigation.NavController
import com.dev.miasnikoff.bookworm.di.FlowNavHolder
import com.dev.miasnikoff.bookworm.di.GlobalNavHolder
import com.dev.miasnikoff.bookworm.utils.navigation.navholder.FlowNavigatorHolder
import com.dev.miasnikoff.bookworm.utils.navigation.navholder.GlobalNavigatorHolder
import com.dev.miasnikoff.bookworm.utils.navigation.navholder.NavigatorHolder
import com.dev.miasnikoff.bookworm.utils.navigation.router.FlowRouter
import com.dev.miasnikoff.bookworm.utils.navigation.router.FlowRouterImpl
import com.dev.miasnikoff.bookworm.utils.navigation.router.GlobalRouter
import com.dev.miasnikoff.bookworm.utils.navigation.router.GlobalRouterImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

    @Provides
    fun provideGlobalRouter(@GlobalNavHolder navigatorHolder: NavigatorHolder<NavController>): GlobalRouter {
        return GlobalRouterImpl(navigatorHolder)
    }

    @Provides
    fun provideFlowRouter(
        globalRouter: GlobalRouter,
        @FlowNavHolder navigatorHolder: NavigatorHolder<NavController>
    ): FlowRouter {
        return FlowRouterImpl(globalRouter, navigatorHolder)
    }
}

@Module
interface NavigationBindModule {

    @Singleton
    @Binds
    @GlobalNavHolder
    fun bindGlobalNavigatorHolder(navigatorHolder: GlobalNavigatorHolder): NavigatorHolder<NavController>

    @Singleton
    @Binds
    @FlowNavHolder
    fun bindFlowNavigatorHolder(navigatorHolder: FlowNavigatorHolder): NavigatorHolder<NavController>
}