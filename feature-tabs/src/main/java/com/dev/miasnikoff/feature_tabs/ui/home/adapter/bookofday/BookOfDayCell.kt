package com.dev.miasnikoff.feature_tabs.ui.home.adapter.bookofday

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.core_ui.adapter.Cell
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.ItemBookOfDayBinding

class BookOfDayCell(private val itemClickListener: ItemClickListener) : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean =
        item is BookOfDayItem

    override fun type(): Int = R.layout.item_book_of_day

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookOfDayBinding.inflate(inflater, parent, false)
        return BookOfDayHolder(binding, itemClickListener)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is BookOfDayHolder && item is BookOfDayItem) {
            holder.bind(item)
        }
    }

    interface ItemClickListener {
        fun onItemClick(itemId: String)
    }
}