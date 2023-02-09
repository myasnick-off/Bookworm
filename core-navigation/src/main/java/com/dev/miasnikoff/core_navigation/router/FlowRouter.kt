package com.dev.miasnikoff.core_navigation.router

import androidx.navigation.NavDirections

interface FlowRouter : GlobalRouter {
    fun setRootScreen()
    fun navigateTo(navDestinations: NavDirections)
}