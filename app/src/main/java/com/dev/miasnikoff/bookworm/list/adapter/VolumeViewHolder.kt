package com.dev.miasnikoff.bookworm.list.adapter

import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.ui.adapter.BaseViewHolder
import com.dev.miasnikoff.bookworm.databinding.ItemVolumeListBinding

class VolumeViewHolder(private val binding: ItemVolumeListBinding) :
    BaseViewHolder<VolumeItem>(binding.root) {

    override fun bind(item: VolumeItem) {
        binding.bookTitle.text = item.title
        binding.bookAuthors.text = item.authors
        Glide.with(binding.bookImage.context)
            .load(item.imageLink)
            .error(R.drawable.ic_broken_image_48)
            .into(binding.bookImage)
    }
}