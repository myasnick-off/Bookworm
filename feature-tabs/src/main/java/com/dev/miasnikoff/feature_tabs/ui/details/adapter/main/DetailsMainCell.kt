package com.dev.miasnikoff.feature_tabs.ui.details.adapter.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.core_ui.adapter.Cell
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.ItemBookDetailsMainBinding

class DetailsMainCell: Cell<RecyclerItem> {
    override fun belongsTo(item: RecyclerItem): Boolean =
        item is DetailsMainItem

    override fun type(): Int = R.layout.item_book_details_main

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookDetailsMainBinding.inflate(inflater, parent, false)
        return DetailsMainHolder(binding)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is DetailsMainHolder && item is DetailsMainItem) {
            holder.bind(item)
        }
    }
}