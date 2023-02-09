package com.dev.miasnikoff.feature_tabs.ui.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.core_ui.adapter.BasePagedListAdapter

class BookListAdapter(
    pageListener: PageListener,
    itemClickListener: BookCell.ItemClickListener
) : BasePagedListAdapter(
    diffUtilItemCallback = ListDiffUtilItemCallback(),
    pageListener = pageListener,
    cells = arrayOf(BookCell(itemClickListener))
) {

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