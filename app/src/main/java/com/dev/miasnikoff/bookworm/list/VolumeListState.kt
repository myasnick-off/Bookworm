package com.dev.miasnikoff.bookworm.list

import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem

sealed class VolumeListState {
    object Loading : VolumeListState()
    data class Success(val data: List<RecyclerItem>) : VolumeListState()
    data class Failure(val message: String) : VolumeListState()
}