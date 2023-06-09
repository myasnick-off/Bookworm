package com.dev.miasnikoff.feature_tabs.domain.model

enum class Filter(val type: String) {
    EBOOKS(type = "ebooks"),
    FREE_BOOKS(type = "free-ebooks"),
    FULL(type = "full"),
    PAID_EBOOKS(type = "paid-ebooks"),
    PARTIAL(type = "partial")
}