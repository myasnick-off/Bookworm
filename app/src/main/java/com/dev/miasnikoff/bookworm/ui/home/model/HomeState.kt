package com.dev.miasnikoff.bookworm.ui.home.model

import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val data: List<RecyclerItem>) : HomeState()
    data class Failure(val message: String) : HomeState()
}