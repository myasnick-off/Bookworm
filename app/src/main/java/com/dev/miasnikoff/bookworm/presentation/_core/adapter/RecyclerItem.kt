package com.dev.miasnikoff.bookworm.presentation._core.adapter

interface RecyclerItem {
    val id: String
    override fun equals(other: Any?): Boolean
}