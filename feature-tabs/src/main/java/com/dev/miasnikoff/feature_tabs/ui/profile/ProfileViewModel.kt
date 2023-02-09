package com.dev.miasnikoff.feature_tabs.ui.profile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val router: FlowRouter
) : ViewModel() {

    fun navigate(direction: NavDirections) {
        router.navigateTo(direction)
    }

    fun back() {
        router.back()
    }
}