package com.dev.miasnikoff.bookworm.presentation.list.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.presentation._core.adapter.BaseViewHolder
import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.databinding.ItemVolumeListBinding
import com.dev.miasnikoff.bookworm.presentation.list.model.VolumeItem
import com.dev.miasnikoff.bookworm.utils.extensions.vibrate

class VolumeViewHolder(
    private val binding: ItemVolumeListBinding,
    private val itemClickListener: VolumeListAdapter.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun bind(item: VolumeItem) {

        binding.bookTitle.text = item.title
        binding.bookAuthors.text = item.authors
        Glide.with(binding.bookImage.context)
            .load(item.imageLink)
            .error(R.drawable.ic_broken_image_48)
            .into(binding.bookImage)
        binding.favoriteIcon.apply {
            setImageDrawable(ContextCompat.getDrawable(context, item.favoriteIcon))
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