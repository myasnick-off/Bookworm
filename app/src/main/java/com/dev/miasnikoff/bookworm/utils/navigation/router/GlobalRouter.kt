package com.dev.miasnikoff.bookworm.utils.navigation.router

interface GlobalRouter {
    fun setNewFlow(startDestinationId: Int)
    fun back()
    fun backToRoot()
}