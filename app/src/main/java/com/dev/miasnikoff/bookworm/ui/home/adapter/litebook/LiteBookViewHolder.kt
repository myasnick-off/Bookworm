package com.dev.miasnikoff.bookworm.ui.home.adapter.litebook

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ItemBookLiteListBinding

class LiteBookViewHolder(
    private val binding: ItemBookLiteListBinding,
    private val itemClickListener: LiteBookCell.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LiteBookItem) {
        binding.bookTitle.text = item.title
        binding.bookAuthors.text = item.authors
        Glide.with(binding.bookImage.context)
            .load(item.imageLink)
            .error(R.drawable.ic_broken_image_48)
            .into(binding.bookImage)
        binding.root.setOnClickListener { itemClickListener.onItemClick(item.id) }
    }
}