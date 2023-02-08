package com.dev.miasnikoff.bookworm.utils.navigation.navholder

import androidx.navigation.NavController

abstract class BaseNavigatorHolder: NavigatorHolder<NavController> {

    private var navigator: NavController? = null
    private var actions: MutableList<NavController.() -> Unit> = mutableListOf()

    override fun bind(navigator: NavController) {
        this.navigator = navigator
        actions.forEach { executeAction(it) }
        actions.clear()
    }

    override fun unbind() {
        navigator = null
    }

    override fun executeAction(action: NavController.() -> Unit) {
        navigator?.apply(action) ?: actions.add(action)
    }
}