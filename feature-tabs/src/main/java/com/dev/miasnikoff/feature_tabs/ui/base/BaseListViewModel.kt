package com.dev.miasnikoff.feature_tabs.ui.base

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dev.miasnikoff.core_navigation.router.FlowRouter
import com.dev.miasnikoff.core_ui.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseListViewModel(router: FlowRouter) : BaseViewModel(router) {

    protected val mScreenState = MutableStateFlow<ListState>(ListState.Empty)
    val screenState = mScreenState.asStateFlow()

    protected val mScreenEvent = MutableSharedFlow<ScreenEvent>()
    val screenEvent = mScreenEvent.asSharedFlow()

    protected var job: Job? = null

    abstract fun getInitialData()

    protected fun postError(message: String? = null) {
        viewModelScope.launch {
            Log.e("###", message ?: "")
            mScreenState.value = ListState.Failure
            mScreenEvent.emit(ScreenEvent.Error)
        }
    }
}