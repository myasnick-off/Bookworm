package com.dev.miasnikoff.bookworm.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ItemBookLiteListBinding
import com.dev.miasnikoff.bookworm.presentation.home.model.HomeBookItem

class HomeBookViewHolder(
    private val binding: ItemBookLiteListBinding,
    private val itemClickListener: HomeBooksAdapter.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HomeBookItem) {

        binding.bookTitle.text = item.title
        binding.bookAuthors.text = item.authors
        Glide.with(binding.bookImage.context)
            .load(item.imageLink)
            .error(R.drawable.ic_broken_image_48)
            .into(binding.bookImage)
        binding.root.setOnClickListener { itemClickListener.onItemClick(item.id) }
    }
}