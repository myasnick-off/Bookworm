package com.dev.miasnikoff.bookworm.utils.navigation.router

import androidx.navigation.NavController
import com.dev.miasnikoff.bookworm.utils.navigation.navholder.NavigatorHolder

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