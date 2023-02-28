package com.dev.miasnikoff.feature_tabs.ui.base

import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseListViewModel(router: FlowRouter) : BaseViewModel(router) {

    protected val mutableStateFlow = MutableStateFlow<ListState>(ListState.Empty)
    val stateFlow = mutableStateFlow.asStateFlow()

    protected val mutableSharedFlow = MutableSharedFlow<String?>()
    val sharedFlow = mutableSharedFlow.asSharedFlow()

    abstract fun getInitialData()
}