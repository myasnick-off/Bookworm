package com.dev.miasnikoff.bookworm.core.ui.adapter

import androidx.recyclerview.widget.DiffUtil

val diffUtilItemCallback = object: DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean =
        oldItem == newItem
}