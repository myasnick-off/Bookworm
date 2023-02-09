package com.dev.miasnikoff.core_navigation.router

import androidx.navigation.NavController
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder

class GlobalRouterImpl (private val navigatorHolder: NavigatorHolder<NavController>) :
    GlobalRouter {

    override fun setNewFlow(startDestinationId: Int) {
        navigatorHolder.executeAction {
            graph.setStartDestination(startDestinationId)
        }
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