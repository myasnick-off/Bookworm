package com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithheader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.core_ui.adapter.Cell
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.ItemTextWithHeaderBinding

class TextWithHeaderCell : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean =
        item is TextWithHeaderItem

    override fun type(): Int = R.layout.item_text_with_header

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTextWithHeaderBinding.inflate(inflater, parent, false)
        return TextWithHeaderHolder(binding)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is TextWithHeaderHolder && item is TextWithHeaderItem) {
            holder.bind(item)
        }
    }
}