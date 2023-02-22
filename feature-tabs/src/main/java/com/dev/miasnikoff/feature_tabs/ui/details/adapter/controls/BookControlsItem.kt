package com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls

import com.dev.miasnikoff.core_ui.R
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

data class BookControlsItem(
    override val id: String,
    val favoriteIcon: Int = R.drawable.ic_bookmark_border_36,
    val previewLink: String
) : RecyclerItem {

    val isPreviewVisible: Boolean
        get() = previewLink.isNotEmpty()
}