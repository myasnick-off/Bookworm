package com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.core_ui.adapter.Cell
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.ItemBookControlsBinding


class BookControlsCell(private val controlsClickListener: ControlsClickListener) :
    Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean =
        item is BookControlsItem

    override fun type(): Int = R.layout.item_book_controls

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookControlsBinding.inflate(inflater, parent, false)
        return BookControlsHolder(binding, controlsClickListener)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is BookControlsHolder && item is BookControlsItem) {
            holder.bind(item)
        }
    }

    interface ControlsClickListener {
        fun onFavoriteClick()
        fun onPreviewClick(url: String)
    }
}