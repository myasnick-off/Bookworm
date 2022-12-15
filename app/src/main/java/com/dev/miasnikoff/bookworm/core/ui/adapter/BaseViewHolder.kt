package com.dev.miasnikoff.bookworm.core.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T : RecyclerItem>(viewItem: View) :
    RecyclerView.ViewHolder(viewItem) {
    abstract fun bind(item: T)
}