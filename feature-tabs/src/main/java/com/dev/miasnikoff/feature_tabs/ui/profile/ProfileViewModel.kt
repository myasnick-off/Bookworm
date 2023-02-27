package com.dev.miasnikoff.feature_tabs.ui.profile

import com.dev.miasnikoff.core_navigation.DeepLink
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val router: FlowRouter
) : BaseViewModel(router) {

    fun navigate(link: DeepLink) {
        router.navigateTo(link)
    }
}