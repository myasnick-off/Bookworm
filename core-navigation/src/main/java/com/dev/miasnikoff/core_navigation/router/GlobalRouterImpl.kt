package com.dev.miasnikoff.core_navigation.router

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.dev.miasnikoff.core_navigation.DeepLink
import com.dev.miasnikoff.core_navigation.navigator.NavigatorHolder

class GlobalRouterImpl (private val navigatorHolder: NavigatorHolder<NavController>) :
    GlobalRouter {

    override fun setNewGraph(graphId: Int) {
        navigatorHolder.executeAction {
            this.graph = navInflater.inflate(graphId)
        }
    }

    override fun navigateTo(link: DeepLink) {
        navigatorHolder.executeAction {
            val request = NavDeepLinkRequest.Builder
                .fromUri(link.uri.toUri())
                .build()
            navigate(request)
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