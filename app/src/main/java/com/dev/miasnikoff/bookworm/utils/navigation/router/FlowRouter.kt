package com.dev.miasnikoff.bookworm.utils.navigation.router

import androidx.navigation.NavDirections

interface FlowRouter : GlobalRouter {
    fun setRootScreen()
    fun navigateTo(navDestinations: NavDirections)
}