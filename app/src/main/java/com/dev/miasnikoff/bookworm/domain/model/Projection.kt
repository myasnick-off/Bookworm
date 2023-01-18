package com.dev.miasnikoff.bookworm.domain.model

enum class Projection(val type: String) {
    FULL(type = "full"),
    LITE(type = "lite")
}