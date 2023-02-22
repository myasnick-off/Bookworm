package com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithlabel

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

data class TextWithLabelItem(
    override val id: String,
    val labelRes: Int,
    val text: String
) : RecyclerItem
