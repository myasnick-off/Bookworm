package com.dev.miasnikoff.bookworm.core.ui.adapter

interface RecyclerItem {
    val id: String
    override fun equals(other: Any?): Boolean
}