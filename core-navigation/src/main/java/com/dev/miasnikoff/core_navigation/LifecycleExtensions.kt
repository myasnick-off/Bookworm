package com.dev.miasnikoff.core_navigation

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get

inline fun <reified VM : ViewModel> Fragment.viewModel(): VM {
    val storeOwner: ViewModelStoreOwner = when {
        this is FlowFragment -> this
        this.parentFragment?.parentFragment is FlowFragment ->
            this.parentFragment?.parentFragment as ViewModelStoreOwner
        else -> error("ViewModel Store not found!")
    }
    return ViewModelProvider(storeOwner).get()
}