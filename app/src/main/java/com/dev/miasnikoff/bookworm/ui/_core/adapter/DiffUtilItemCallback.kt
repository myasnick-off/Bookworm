package com.dev.miasnikoff.bookworm.ui._core.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookItem

val diffUtilItemCallback = object : DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean =
        oldItem == newItem

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