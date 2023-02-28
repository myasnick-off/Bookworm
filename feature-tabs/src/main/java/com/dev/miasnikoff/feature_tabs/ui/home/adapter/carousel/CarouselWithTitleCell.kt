package com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.core_ui.adapter.Cell
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.ItemCarouselWithTitleListBinding

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