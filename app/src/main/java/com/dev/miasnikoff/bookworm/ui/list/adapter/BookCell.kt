package com.dev.miasnikoff.bookworm.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ItemBookListBinding
import com.dev.miasnikoff.bookworm.ui._core.adapter.Cell
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

class BookCell(private val itemClickListener: ItemClickListener): Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean =
        item is BookItem

    override fun type(): Int = R.layout.item_book_list

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookListBinding.inflate(inflater, parent, false)
        return BookViewHolder(binding, itemClickListener)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is BookViewHolder && item is BookItem) {
            holder.bind(item)
        }
    }

    interface ItemClickListener {
        fun onItemClick(itemId: String)
        fun onItemLongClick(itemId: String)
        fun onFavoriteClick(itemId: String)
    }
}