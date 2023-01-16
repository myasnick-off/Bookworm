package com.dev.miasnikoff.bookworm.ui.home.adapter.bookofday

import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

data class BookOfDayItem(
    override val id: String,
    val title: String,
    val authors: String,
    val publisher: String,
    val publishedDate: String,
    val averageRating: Float,
    val imageLink: String?
    ) : RecyclerItem