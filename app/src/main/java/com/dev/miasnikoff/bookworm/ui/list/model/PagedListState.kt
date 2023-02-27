package com.dev.miasnikoff.bookworm.ui.list.model

import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

sealed class PagedListState {
    object Loading : PagedListState()
    object MoreLoading : PagedListState()
    data class Success(val data: List<RecyclerItem>, val loadMore: Boolean) : PagedListState()
    data class Failure(val message: String) : PagedListState()
}