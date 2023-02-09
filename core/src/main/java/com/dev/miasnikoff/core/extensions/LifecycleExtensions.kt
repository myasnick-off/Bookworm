package com.dev.miasnikoff.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get
import androidx.navigation.fragment.NavHostFragment

inline fun <reified VM: ViewModel> Fragment.viewModel(): VM {
    val storeOwner: ViewModelStoreOwner = when {
        this is NavHostFragment -> this
        this.parentFragment is NavHostFragment -> this.parentFragment as ViewModelStoreOwner
        else -> error("ViewModel Store not found!")
    }
    return ViewModelProvider(storeOwner).get()
}