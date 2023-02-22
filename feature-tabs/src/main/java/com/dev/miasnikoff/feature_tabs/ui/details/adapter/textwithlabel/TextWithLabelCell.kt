package com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithlabel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.core_ui.adapter.Cell
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.ItemTextWithLabelBinding

class TextWithLabelCell : Cell<RecyclerItem> {

    override fun belongsTo(item: RecyclerItem): Boolean =
        item is TextWithLabelItem

    override fun type(): Int = R.layout.item_text_with_label

    override fun holder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTextWithLabelBinding.inflate(inflater, parent, false)
        return TextWithLabelHolder(binding)
    }

    override fun bind(holder: RecyclerView.ViewHolder, item: RecyclerItem) {
        if (holder is TextWithLabelHolder && item is TextWithLabelItem) {
            holder.bind(item)
        }
    }
}