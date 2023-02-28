package com.dev.miasnikoff.core_navigation.router

import com.dev.miasnikoff.core_navigation.DeepLink

interface GlobalRouter {
    fun setNewGraph(graphId: Int)
    fun navigateTo(link: DeepLink)
    fun back()
    fun backToRoot()
}