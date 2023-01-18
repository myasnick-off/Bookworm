package com.dev.miasnikoff.bookworm.ui.list.adapter

import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ItemBookListBinding
import com.dev.miasnikoff.bookworm.utils.extensions.vibrate

class BookViewHolder(
    private val binding: ItemBookListBinding,
    private val itemClickListener: BookCell.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BookItem) {

        binding.bookTitle.text = item.title
        binding.bookAuthors.text = item.authors
        Glide.with(binding.bookImage.context)
            .load(item.imageLink)
            .error(R.drawable.ic_broken_image_48)
            .into(binding.bookImage)
        binding.favoriteIcon.apply {
            setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    item.favoriteIcon,
                    binding.favoriteIcon.context.theme
                )
            )
            setOnClickListener { itemClickListener.onFavoriteClick(item.id) }
        }
        binding.root.apply {
            setOnClickListener { itemClickListener.onItemClick(item.id) }
            setOnLongClickListener {
                vibrate(VIBRATE_DURATION)
                itemClickListener.onItemLongClick(item.id)
            }
        }
    }

    fun updateFavorite(iconRes: Int) {
        binding.favoriteIcon
            .setImageDrawable(ContextCompat.getDrawable(binding.favoriteIcon.context, iconRes))
    }

    companion object {
        private const val VIBRATE_DURATION = 50L
    }
}