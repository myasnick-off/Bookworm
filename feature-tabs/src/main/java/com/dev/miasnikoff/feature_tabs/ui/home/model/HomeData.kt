package com.dev.miasnikoff.feature_tabs.ui.home.model

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.genre.GenreItem

data class HomeData(
    val bookOfDay: RecyclerItem,
    val popularGenres: List<GenreItem>,
    val lastSeen: List<RecyclerItem>,
    val newest: List<RecyclerItem>,
    val popularFree: List<RecyclerItem>,
)
