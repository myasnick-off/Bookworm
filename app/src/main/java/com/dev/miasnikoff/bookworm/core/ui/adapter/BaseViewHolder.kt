package com.dev.miasnikoff.bookworm.core.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(viewItem: View) :
    RecyclerView.ViewHolder(viewItem) {
    abstract fun bind(item: RecyclerItem, position: Int)
}