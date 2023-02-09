package com.dev.miasnikoff.feature_tabs.ui.list.adapter

import com.dev.miasnikoff.core_ui.adapter.BaseDiffUtilItemCallback
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem

class ListDiffUtilItemCallback : BaseDiffUtilItemCallback() {

    override fun getChangePayload(oldItem: RecyclerItem, newItem: RecyclerItem): Any? {
        if (oldItem is BookItem && newItem is BookItem) {
            if (oldItem.id == newItem.id) {
                return if (oldItem.favoriteIcon == newItem.favoriteIcon)
                    super.getChangePayload(oldItem, newItem)
                else newItem.favoriteIcon
            }
        }
        return super.getChangePayload(oldItem, newItem)
    }
}