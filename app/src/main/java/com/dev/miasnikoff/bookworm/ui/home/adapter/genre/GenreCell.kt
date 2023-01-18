package com.dev.miasnikoff.bookworm.ui.home.adapter.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ItemGenreListBinding
import com.dev.miasnikoff.bookworm.ui._core.adapter.Cell
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

class GenreCell(private val itemClickListener: ItemClickListener) : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean =
        item is GenreItem

    override fun type(): Int = R.layout.item_genre_list

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreListBinding.inflate(inflater, parent, false)
        return GenreViewHolder(binding, itemClickListener)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is GenreViewHolder && item is GenreItem) {
            holder.bind(item)
        }
    }

    interface ItemClickListener {
        fun onItemClick(query: String)
    }
}