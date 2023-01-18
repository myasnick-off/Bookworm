package com.dev.miasnikoff.bookworm.ui.home.adapter.bookofday

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ItemBookOfDayBinding
import com.dev.miasnikoff.bookworm.ui._core.adapter.Cell
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

class BookOfDayCell(private val itemClickListener: ItemClickListener) : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean =
        item is BookOfDayItem

    override fun type(): Int = R.layout.item_book_of_day

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookOfDayBinding.inflate(inflater, parent, false)
        return BookOfDayViewHolder(binding, itemClickListener)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is BookOfDayViewHolder && item is BookOfDayItem) {
            holder.bind(item)
        }
    }

    interface ItemClickListener {
        fun onItemClick(itemId: String)
    }
}