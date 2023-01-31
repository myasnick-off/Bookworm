package com.dev.miasnikoff.bookworm.ui.home.mapper

import com.dev.miasnikoff.bookworm.data.local.model.BookEntity
import com.dev.miasnikoff.bookworm.data.remote.model.ImageSize
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui._core.mapper.BaseUiDataMapper
import com.dev.miasnikoff.bookworm.ui.home.adapter.bookofday.BookOfDayItem
import com.dev.miasnikoff.bookworm.ui.home.adapter.litebook.LiteBookItem
import javax.inject.Inject

class HomeEntityToUiMapper @Inject constructor(): BaseUiDataMapper<BookEntity>() {

    override fun toItem(item: BookEntity, imageSize: ImageSize): RecyclerItem =
        LiteBookItem(
            id = item.id,
            title = item.title.orEmpty(),
            authors = item.authors.orEmpty(),
            averageRating = item.averageRating ?: 0f,
            imageLink = item.imageLinkSmall
        )

    fun toSingleItem(item: BookEntity?): RecyclerItem? =
        item?.let {
            BookOfDayItem(
                id = item.id,
                title = item.title.orEmpty(),
                authors = item.authors.orEmpty(),
                publisher = item.publisher.orEmpty(),
                publishedDate = item.publishedDate.orEmpty(),
                averageRating = item.averageRating ?: 0f,
                imageLink = item.imageLinkLarge
            )
        }
}