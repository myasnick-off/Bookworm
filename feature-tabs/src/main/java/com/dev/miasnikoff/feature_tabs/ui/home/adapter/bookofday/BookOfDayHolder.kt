package com.dev.miasnikoff.feature_tabs.ui.home.adapter.bookofday

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.miasnikoff.core_ui.extensions.getLocalizedQuantityString
import com.dev.miasnikoff.feature_tabs.databinding.ItemBookOfDayBinding

class BookOfDayHolder(
    private val binding: ItemBookOfDayBinding,
    private val itemClickListener: BookOfDayCell.ItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BookOfDayItem) = with(binding) {
        bookTitle.text = item.title
        bookAuthors.text = item.authors
        publisher.text = item.publisher
        publishedDate.text = item.publishedDate
        ratingBar.rating = item.averageRating
        bookRating.apply {
            isVisible = item.hasRating
            text = item.averageRating.toString()
        }
        ratingCount.text = if (item.hasRating) root.getLocalizedQuantityString(
            com.dev.miasnikoff.core_ui.R.plurals.rating_count_suffix,
            item.ratingsCount
        ) else root.context.getString(com.dev.miasnikoff.core_ui.R.string.no_ratings)
        Glide.with(bookImage.context)
            .load(item.imageLink)
            .error(com.dev.miasnikoff.core_ui.R.drawable.ic_broken_image_48)
            .into(bookImage)
        root.setOnClickListener {
            itemClickListener.onItemClick(itemId = item.id)
        }
    }
}