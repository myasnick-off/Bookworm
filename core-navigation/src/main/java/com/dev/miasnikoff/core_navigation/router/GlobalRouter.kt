package com.dev.miasnikoff.core_navigation.router

interface GlobalRouter {
    fun setNewFlow(startDestinationId: Int)
    fun back()
    fun backToRoot()
}