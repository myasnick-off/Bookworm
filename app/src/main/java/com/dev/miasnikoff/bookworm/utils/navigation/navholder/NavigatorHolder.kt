package com.dev.miasnikoff.bookworm.utils.navigation.navholder

interface NavigatorHolder<T> {
    fun bind(navigator: T)
    fun unbind()
    fun executeAction(action: T.() -> Unit)
}