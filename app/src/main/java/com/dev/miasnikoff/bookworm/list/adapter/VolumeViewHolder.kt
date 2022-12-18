package com.dev.miasnikoff.bookworm.list.adapter

import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.ui.adapter.BaseViewHolder
import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.databinding.ItemVolumeListBinding

class VolumeViewHolder(
    private val binding: ItemVolumeListBinding,
    private val itemClickListener: VolumeListAdapter.ItemClickListener
) :
    BaseViewHolder(binding.root) {

    override fun bind(item: RecyclerItem) {
        if (item is VolumeItem) {
            binding.bookTitle.text = item.title
            binding.bookAuthors.text = item.authors
            Glide.with(binding.bookImage.context)
                .load(item.imageLink)
                .error(R.drawable.ic_broken_image_48)
                .into(binding.bookImage)
            binding.favoriteIcon.apply {
                setImageDrawable( ContextCompat.getDrawable(context, item.favoriteIcon))
                setOnClickListener { itemClickListener.onIconClick(item.id) }
            }
            binding.root.apply {
                setOnClickListener { itemClickListener.onItemClick() }
                setOnLongClickListener { itemClickListener.onItemLongClick() }
            }
        }
    }
}