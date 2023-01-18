package com.dev.miasnikoff.bookworm.ui.home.adapter.litebook

import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

data class LiteBookItem(
    override val id: String,
    val title: String,
    val authors: String,
    val averageRating: Float,
    val imageLink: String?,
    ) : RecyclerItem