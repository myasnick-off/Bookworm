package com.dev.miasnikoff.core_navigation.router

import androidx.navigation.NavDirections
import com.dev.miasnikoff.core_navigation.DeepLink

interface FlowRouter {
    fun navigateTo(navDestinations: NavDirections)
    fun navigateTo(link: DeepLink)
    fun back()
    fun backToRoot()
}