package com.dev.miasnikoff.bookworm.list

import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem

interface ListView {
    fun showList(volumes: List<RecyclerItem>)
    fun showError(message: String)

}