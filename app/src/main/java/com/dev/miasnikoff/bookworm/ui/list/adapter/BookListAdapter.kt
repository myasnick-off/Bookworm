package com.dev.miasnikoff.bookworm.ui.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.bookworm.ui._core.adapter.BasePagedListAdapter

class BookListAdapter(
    pageListener: PageListener,
    itemClickListener: BookCell.ItemClickListener
) : BasePagedListAdapter(pageListener, BookCell(itemClickListener)) {

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty() || payloads.any { it !is Int }) {
            super.onBindViewHolder(holder, position)
        } else {
            val iconRes = payloads.first { it is Int } as Int
            (holder as? BookViewHolder)?.updateFavorite(iconRes)
        }
    }
}