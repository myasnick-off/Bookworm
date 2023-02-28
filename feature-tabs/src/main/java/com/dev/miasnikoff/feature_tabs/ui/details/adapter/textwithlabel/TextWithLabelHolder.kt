package com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithlabel

import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.feature_tabs.databinding.ItemTextWithLabelBinding

class TextWithLabelHolder(
    private val binding: ItemTextWithLabelBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TextWithLabelItem) = with(binding) {
        itemLabel.text = root.context.getText(item.labelRes)
        itemContent.text = item.text
    }
}