package com.dev.miasnikoff.bookworm.domain.model

enum class OrderBy(val type: String) {
    NEWEST(type = "newest"),
    RELEVANCE(type = "relevance")
}