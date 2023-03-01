package com.dev.miasnikoff.feature_tabs.ui.home.adapter.litebook

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

data class LiteBookItem(
    override val id: String,
    val title: String,
    val authors: String,
    val imageLink: String?,
    ) : RecyclerItem