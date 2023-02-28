package com.dev.miasnikoff.feature_tabs.ui.list.adapter

import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.miasnikoff.core_ui.extensions.vibrate
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.ItemBookListBinding

class BookHolder(
    private val binding: ItemBookListBinding,
    private val itemClickListener: BookCell.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BookItem) {

        binding.bookTitle.text = item.title
        binding.bookAuthors.text = item.authors
        Glide.with(binding.bookImage.context)
            .load(item.imageLink)
            .error(com.dev.miasnikoff.core_ui.R.drawable.ic_broken_image_48)
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
                showPopUpMenu(itemView) { itemClickListener.onItemLongClick(item.id) }
                true
            }
        }
    }

    fun updateFavorite(iconRes: Int) {
        binding.favoriteIcon
            .setImageDrawable(ContextCompat.getDrawable(binding.favoriteIcon.context, iconRes))
    }

    private fun showPopUpMenu(
        anchor: View,
        menuRes: Int = R.menu.menu_popup,
        action: () -> Unit
    ) {
        PopupMenu(binding.root.context, anchor).apply {
            menuInflater.inflate(menuRes, menu)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_remove -> {
                        action()
                        true
                    }
                    else -> false
                }
            }
            show()
        }
    }

    companion object {
        private const val VIBRATE_DURATION = 50L
    }
}