package com.dev.miasnikoff.feature_tabs.ui.list.model

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

sealed class PagedListState {
    object Empty : PagedListState()
    object Loading : PagedListState()
    object MoreLoading : PagedListState()
    data class Success(val data: List<RecyclerItem>, val loadMore: Boolean) : PagedListState()
    data class Failure(val message: String) : PagedListState()
}