package com.dev.miasnikoff.core_navigation.router

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder

class FlowRouterImpl (
    private val globalRouter: GlobalRouter,
    private val navigatorHolder: NavigatorHolder<NavController>
    ) :
    FlowRouter {

    override fun setRootScreen() {
        //TODO("Not yet implemented")
    }

    override fun navigateTo(navDestinations: NavDirections) {
        navigatorHolder.executeAction {
            navigate(navDestinations)
        }
    }

    override fun setNewFlow(startDestinationId: Int) {
        globalRouter.setNewFlow(startDestinationId)
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