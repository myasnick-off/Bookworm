package com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithheader

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

data class TextWithHeaderItem(
    override val id: String,
    val headerRes: Int,
    val text: String
) : RecyclerItem
