package com.dev.miasnikoff.feature_tabs.ui.details.model

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

sealed class DetailsState {
    object Empty : DetailsState()
    object Loading : DetailsState()
    data class Success(val data: List<RecyclerItem>) : DetailsState()
    data class Failure(val message: String) : DetailsState()
}