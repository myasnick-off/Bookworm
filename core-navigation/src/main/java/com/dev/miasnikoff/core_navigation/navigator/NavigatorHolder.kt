package com.dev.miasnikoff.core_navigation.navigator

interface NavigatorHolder<T> {
    fun bind(navigator: T)
    fun unbind()
    fun executeAction(action: T.() -> Unit)
}