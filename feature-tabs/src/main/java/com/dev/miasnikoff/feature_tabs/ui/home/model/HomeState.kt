package com.dev.miasnikoff.feature_tabs.ui.home.model

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

sealed class HomeState {
    object Empty : HomeState()
    object Loading : HomeState()
    data class Success(val data: List<RecyclerItem>) : HomeState()
    data class Failure(val message: String) : HomeState()
}