package com.dev.miasnikoff.feature_tabs.ui.home.adapter.genre

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.miasnikoff.feature_tabs.databinding.ItemGenreListBinding

class GenreHolder(
    private val binding: ItemGenreListBinding,
    private val itemClickListener: GenreCell.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GenreItem) {
        val context = binding.root.context
        binding.genreName.text = context.getText(item.genreData.titleRes)
        Glide.with(binding.genreImage.context)
            .load(item.genreData.imgResId)
            .error(com.dev.miasnikoff.core_ui.R.drawable.ic_broken_image_48)
            .into(binding.genreImage)
        binding.root.setOnClickListener { itemClickListener.onItemClick(item.genreData.query) }
    }
}