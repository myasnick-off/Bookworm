package com.dev.miasnikoff.core_ui.adapter

interface RecyclerItem {
    val id: String
    override fun equals(other: Any?): Boolean
}