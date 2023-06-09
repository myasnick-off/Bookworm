package com.dev.miasnikoff.feature_tabs.ui.home.adapter.litebook

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.miasnikoff.feature_tabs.databinding.ItemBookLiteListBinding

class LiteBookHolder(
    private val binding: ItemBookLiteListBinding,
    private val itemClickListener: LiteBookCell.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LiteBookItem) {
        binding.bookTitle.text = item.title
        binding.bookAuthors.text = item.authors
        Glide.with(binding.bookImage.context)
            .load(item.imageLink)
            .error(com.dev.miasnikoff.core_ui.R.drawable.ic_broken_image_48)
            .into(binding.bookImage)
        binding.root.setOnClickListener { itemClickListener.onItemClick(item.id) }
    }
}