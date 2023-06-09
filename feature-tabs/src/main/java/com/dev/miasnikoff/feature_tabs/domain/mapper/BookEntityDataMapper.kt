package com.dev.miasnikoff.feature_tabs.domain.mapper

import com.dev.miasnikoff.feature_tabs.data.local.BookEntity
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import javax.inject.Inject

class BookEntityDataMapper @Inject constructor() {

    fun toFavorite(item: BookItem): BookEntity =
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
                ratingsCount = 0,
                imageLinkSmall = item.imageLink,
                imageLinkLarge = null,
                language = item.language,
                previewLink = null,
                infoLink = null,
                canonicalVolumeLink = null,
                inFavorite = isFavorite
        )
}