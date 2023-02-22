package com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithheader

import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.feature_tabs.databinding.ItemTextWithHeaderBinding

class TextWithHeaderHolder(
    private val binding: ItemTextWithHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TextWithHeaderItem) = with(binding) {
        itemHeader.text = root.context.getText(item.headerRes)
        itemContent.text = item.text
    }
}