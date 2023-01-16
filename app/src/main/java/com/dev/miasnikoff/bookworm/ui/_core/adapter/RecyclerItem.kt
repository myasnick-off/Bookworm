package com.dev.miasnikoff.bookworm.ui._core.adapter

interface RecyclerItem {
    val id: String
    override fun equals(other: Any?): Boolean
}