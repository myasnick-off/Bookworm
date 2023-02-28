package com.dev.miasnikoff.core_ui.adapter

import androidx.recyclerview.widget.DiffUtil

open class BaseDiffUtilItemCallback : DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean =
        oldItem == newItem
}