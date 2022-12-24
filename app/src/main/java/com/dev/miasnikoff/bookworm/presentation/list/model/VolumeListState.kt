package com.dev.miasnikoff.bookworm.presentation.list.model

import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem

sealed class VolumeListState {
    object Loading : VolumeListState()
    object MoreLoading : VolumeListState()
    data class Success(val data: List<RecyclerItem>, val loadMore: Boolean) : VolumeListState()
    data class Failure(val message: String) : VolumeListState()
}