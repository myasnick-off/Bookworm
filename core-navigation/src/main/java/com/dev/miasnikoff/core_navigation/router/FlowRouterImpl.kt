package com.dev.miasnikoff.core_navigation.router

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.dev.miasnikoff.core_navigation.DeepLink
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder

class FlowRouterImpl(
    private val globalRouter: GlobalRouter,
    private val navigatorHolder: NavigatorHolder<NavController>
) : FlowRouter {

    override fun navigateTo(navDestinations: NavDirections) {
        navigatorHolder.executeAction {
            navigate(navDestinations)
        }
    }

    override fun navigateTo(link: DeepLink) {
        globalRouter.navigateTo(link)
    }

    override fun back() {
        navigatorHolder.executeAction {
            popBackStack()
        }
    }

    override fun backToRoot() {
        navigatorHolder.executeAction {
            backToRoot()
        }
    }
}