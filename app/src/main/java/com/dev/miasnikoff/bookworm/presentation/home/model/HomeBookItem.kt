package com.dev.miasnikoff.bookworm.presentation.home.model

import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem

data class HomeBookItem(
    override val id: String,
    val title: String,
    val authors: String,
    val publisher: String,
    val publishedDate: String,
    val averageRating: Float,
    val imageLink: String?,
    ) : RecyclerItem