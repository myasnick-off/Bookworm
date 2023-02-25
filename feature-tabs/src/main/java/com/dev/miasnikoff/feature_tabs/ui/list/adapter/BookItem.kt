package com.dev.miasnikoff.feature_tabs.ui.list.adapter

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R

data class BookItem(
    override val id: String,
    val title: String,
    val authors: String,
    val publishedDate: String,
    val mainCategory: String,
    val averageRating: Float,
    val imageLink: String?,
    val language: String,
    val isFavorite: Boolean = false,
    val favoriteIcon: Int = R.drawable.ic_bookmark_border_24
    ) : RecyclerItem