package com.dev.miasnikoff.bookworm.ui.profile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.dev.miasnikoff.bookworm.utils.navigation.router.FlowRouter
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