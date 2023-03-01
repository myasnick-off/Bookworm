package com.dev.miasnikoff.core.event

sealed class AppEvent {
    data class FavoriteUpdate(val bookId: String? = null): AppEvent()
    data class HistoryUpdate(val bookId: String? = null): AppEvent()
}
