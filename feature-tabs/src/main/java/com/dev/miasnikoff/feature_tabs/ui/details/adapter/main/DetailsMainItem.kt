package com.dev.miasnikoff.feature_tabs.ui.details.adapter.main

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

data class DetailsMainItem(
    override val id: String,
    val title: String,
    val authors: String,
    val publisher: String,
    val averageRating: Float,
    val ratingsCount: Int,
    val imageLink: String?
) : RecyclerItem {

    val hasRating: Boolean
        get() = averageRating > 0 && ratingsCount > 0
}
