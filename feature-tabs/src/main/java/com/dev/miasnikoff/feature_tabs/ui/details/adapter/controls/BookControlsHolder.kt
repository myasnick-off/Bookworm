package com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.core_ui.extensions.setImageById
import com.dev.miasnikoff.feature_tabs.databinding.ItemBookControlsBinding


class BookControlsHolder(
    private val binding: ItemBookControlsBinding,
    private val controlsClickListener: BookControlsCell.ControlsClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BookControlsItem) = with(binding) {
        favoriteButton.apply {
            setImageById(item.favoriteIcon)
            setOnClickListener { controlsClickListener.onFavoriteClick() }
        }
        previewButton.apply {
            isVisible = item.isPreviewVisible
            setOnClickListener { controlsClickListener.onPreviewClick(item.previewLink) }
        }
    }

    fun updateFavoriteButton(iconRes: Int) {
        binding.favoriteButton.setImageById(iconRes)
    }
}