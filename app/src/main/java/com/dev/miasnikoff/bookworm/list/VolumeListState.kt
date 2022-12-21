package com.dev.miasnikoff.bookworm.list

import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem

sealed class VolumeListState {
    object Loading : VolumeListState()
    object MoreLoading : VolumeListState()
    data class Success(val data: List<RecyclerItem>, val loadMore: Boolean) : VolumeListState()
    data class Failure(val message: String) : VolumeListState()
}