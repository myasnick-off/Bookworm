package com.dev.miasnikoff.feature_tabs.ui.home.adapter.bookofday

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

data class BookOfDayItem(
    override val id: String,
    val title: String,
    val authors: String,
    val publisher: String,
    val publishedDate: String,
    val averageRating: Float,
    val averageRatingTxt: String,
    val ratingsCount: Int,
    val imageLink: String?,
    val hasRating: Boolean
) : RecyclerItem