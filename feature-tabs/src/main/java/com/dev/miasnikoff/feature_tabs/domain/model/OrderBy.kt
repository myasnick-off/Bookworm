package com.dev.miasnikoff.feature_tabs.domain.model

enum class OrderBy(val type: String) {
    NEWEST(type = "newest"),
    RELEVANCE(type = "relevance")
}