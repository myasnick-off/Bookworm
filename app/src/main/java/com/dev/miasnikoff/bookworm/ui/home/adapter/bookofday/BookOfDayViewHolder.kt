package com.dev.miasnikoff.bookworm.ui.home.adapter.bookofday

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ItemBookOfDayBinding

class BookOfDayViewHolder(
    private val binding: ItemBookOfDayBinding,
    private val itemClickListener: BookOfDayCell.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BookOfDayItem) {
        binding.bookTitle.text = item.title
        binding.bookAuthors.text = item.authors
        binding.publisher.text = item.publisher
        binding.publishedDate.text = item.publishedDate
        binding.ratingBar.rating = item.averageRating
        Glide.with(binding.bookImage.context)
            .load(item.imageLink)
            .error(R.drawable.ic_broken_image_48)
            .into(binding.bookImage)
        binding.root.setOnClickListener {
            itemClickListener.onItemClick(itemId = item.id)
        }
    }
}