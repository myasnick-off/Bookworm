package com.dev.miasnikoff.bookworm.presentation.home.adapter

import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ItemHomeListBinding
import com.dev.miasnikoff.bookworm.databinding.ItemVolumeListBinding
import com.dev.miasnikoff.bookworm.presentation.home.model.HomeBookItem
import com.dev.miasnikoff.bookworm.presentation.list.model.VolumeItem
import com.dev.miasnikoff.bookworm.utils.extensions.vibrate

class HomeBookViewHolder(
    private val binding: ItemHomeListBinding,
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