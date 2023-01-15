package com.dev.miasnikoff.bookworm.presentation._core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface Cell<T> {
    fun belongsTo(item: T): Boolean
    fun type(): Int
    fun holder(parent: ViewGroup): RecyclerView.ViewHolder
    fun bind(holder: RecyclerView.ViewHolder, item: T)
}