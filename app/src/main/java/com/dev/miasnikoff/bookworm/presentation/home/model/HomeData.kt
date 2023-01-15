package com.dev.miasnikoff.bookworm.presentation.home.model

import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem

data class HomeData(
    val bookOfDay: RecyclerItem,
    val popularGenres: List<Genre>,
    val lastSeen: List<RecyclerItem>,
    val newest: List<RecyclerItem>,
    val popularFree: List<RecyclerItem>,
)
