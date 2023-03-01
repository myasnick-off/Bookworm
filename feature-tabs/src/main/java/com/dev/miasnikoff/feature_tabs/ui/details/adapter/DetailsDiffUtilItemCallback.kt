package com.dev.miasnikoff.feature_tabs.ui.details.adapter

import com.dev.miasnikoff.core_ui.adapter.BaseDiffUtilItemCallback
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls.BookControlsItem

class DetailsDiffUtilItemCallback : BaseDiffUtilItemCallback() {

    override fun getChangePayload(oldItem: RecyclerItem, newItem: RecyclerItem): Any? {
        if (oldItem is BookControlsItem && newItem is BookControlsItem) {
            if (oldItem.id == newItem.id) {
                return if (oldItem.favoriteIcon == newItem.favoriteIcon)
                    super.getChangePayload(oldItem, newItem)
                else newItem.favoriteIcon
            }
        }
        return super.getChangePayload(oldItem, newItem)
    }
}