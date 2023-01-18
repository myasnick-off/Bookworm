package com.dev.miasnikoff.bookworm.ui.home.adapter.litebook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ItemBookLiteListBinding
import com.dev.miasnikoff.bookworm.ui._core.adapter.Cell
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

class LiteBookCell(private val itemClickListener: ItemClickListener) : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean =
        item is LiteBookItem

    override fun type(): Int = R.layout.item_book_lite_list

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookLiteListBinding.inflate(inflater, parent, false)
        return LiteBookViewHolder(binding, itemClickListener)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is LiteBookViewHolder && item is LiteBookItem) {
            holder.bind(item)
        }
    }

    interface ItemClickListener {
        fun onItemClick(itemId: String)
    }
}