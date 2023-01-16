package com.dev.miasnikoff.bookworm.ui.home.model

import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui.home.adapter.genre.GenreItem

data class HomeData(
    val bookOfDay: RecyclerItem,
    val popularGenres: List<GenreItem>,
    val lastSeen: List<RecyclerItem>,
    val newest: List<RecyclerItem>,
    val popularFree: List<RecyclerItem>,
)
