package com.dev.miasnikoff.bookworm.list.adapter

import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem

data class VolumeItem(
    override val id: String,
    val title: String,
    val authors: String,
    val publishedDate: String,
    val mainCategory: String,
    val averageRating: Float,
    val imageLink: String?,
    val language: String
    ) : RecyclerItem