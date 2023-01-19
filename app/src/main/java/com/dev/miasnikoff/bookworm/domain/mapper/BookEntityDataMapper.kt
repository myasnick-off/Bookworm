package com.dev.miasnikoff.bookworm.domain.mapper

import com.dev.miasnikoff.bookworm.data.local.model.BookEntity
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookItem

class BookEntityDataMapper {

    fun toFavorite(item: BookItem):BookEntity =
        toEntity(item, true)

    private fun toEntity(item: BookItem, isFavorite: Boolean): BookEntity =
            BookEntity(
                id = item.id,
                title = item.title,
                subtitle = null,
                authors = item.authors,
                publisher = null,
                publishedDate = item.publishedDate,
                description = null,
                pageCount = null,
                printType = null,
                mainCategory = null,
                categories = null,
                averageRating = item.averageRating,
                ratingsCount = null,
                imageLinkSmall = item.imageLink,
                imageLinkLarge = null,
                language = item.language,
                previewLink = null,
                infoLink = null,
                canonicalVolumeLink = null,
                inFavorite = isFavorite
        )
}