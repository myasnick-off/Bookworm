package com.dev.miasnikoff.core_ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.dev.miasnikoff.core_navigation.router.FlowRouter

abstract class BaseViewModel(private val router: FlowRouter) : ViewModel() {

    fun navigate(direction: NavDirections) {
        router.navigateTo(direction)
    }

    fun back() {
        router.back()
    }
}