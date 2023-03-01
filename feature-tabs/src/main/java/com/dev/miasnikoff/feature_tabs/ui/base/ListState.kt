package com.dev.miasnikoff.feature_tabs.ui.base

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

sealed class ListState {
    object Empty : ListState()
    object EmptyLoading : ListState()
    object Loading : ListState()
    data class Success(val data: List<RecyclerItem>, val loadMore: Boolean = false) : ListState()
    object Failure : ListState()
}