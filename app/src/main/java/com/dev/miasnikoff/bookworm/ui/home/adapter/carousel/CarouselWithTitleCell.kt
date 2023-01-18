package com.dev.miasnikoff.bookworm.ui.home.adapter.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ItemCarouselWithTitleListBinding
import com.dev.miasnikoff.bookworm.ui._core.adapter.Cell
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

class CarouselWithTitleCell(private val itemClickListener: ItemClickListener) : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean =
        item is CarouselWithTitleItem

    override fun type(): Int = R.layout.item_carousel_with_title_list

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarouselWithTitleListBinding.inflate(inflater, parent, false)
        return CarouselWithTitleViewHolder(binding, itemClickListener)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is CarouselWithTitleViewHolder && item is CarouselWithTitleItem) {
            holder.bind(item)
        }
    }

    interface ItemClickListener {
        fun onTitleClick(category: Category)
        fun onGenreClick(query: String)
        fun onBookClick(bookId: String)
    }
}