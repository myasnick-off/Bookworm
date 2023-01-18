package com.dev.miasnikoff.bookworm.ui.home.adapter.carousel

import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.bookworm.databinding.ItemCarouselWithTitleListBinding
import com.dev.miasnikoff.bookworm.ui.home.adapter.CarouselAdapter
import com.dev.miasnikoff.bookworm.ui.home.adapter.genre.GenreCell
import com.dev.miasnikoff.bookworm.ui.home.adapter.litebook.LiteBookCell

class CarouselWithTitleViewHolder(
    private val binding: ItemCarouselWithTitleListBinding,
    private val clickListener: CarouselWithTitleCell.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private val genreClickListener = object : GenreCell.ItemClickListener {
        override fun onItemClick(query: String) {
            clickListener.onGenreClick(query)
        }
    }

    private val bookClickListener = object : LiteBookCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            clickListener.onBookClick(itemId)
        }
    }
    private val carouselAdapter: CarouselAdapter = CarouselAdapter(genreClickListener, bookClickListener)

    init {
        binding.carouselList.adapter = carouselAdapter
    }

    fun bind(item: CarouselWithTitleItem) {
        val context = binding.root.context
        binding.carouselTitle.apply {
            text = context.getText(item.category.titleRes)
            setOnClickListener { clickListener.onTitleClick(item.category) }
        }
        carouselAdapter.submitList(item.items)
    }
}