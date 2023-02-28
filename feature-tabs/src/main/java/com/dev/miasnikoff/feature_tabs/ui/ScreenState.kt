package com.dev.miasnikoff.feature_tabs.ui

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

data class ScreenState(
    val isEmpty: Boolean = true,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val data: List<RecyclerItem> = listOf(),
    val loadMore: Boolean = false
)
