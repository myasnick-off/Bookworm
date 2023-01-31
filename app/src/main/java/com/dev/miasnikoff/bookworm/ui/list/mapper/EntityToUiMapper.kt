package com.dev.miasnikoff.bookworm.ui.list.mapper

import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.data.local.model.BookEntity
import com.dev.miasnikoff.bookworm.data.remote.model.ImageSize
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui._core.mapper.BaseUiDataMapper
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookItem
import javax.inject.Inject

class EntityToUiMapper @Inject constructor() : BaseUiDataMapper<BookEntity>() {

    override fun toItem(item: BookEntity, imageSize: ImageSize): RecyclerItem =
        BookItem(
            id = item.id,
            title = item.title.orEmpty(),
            authors = item.authors.orEmpty(),
            publishedDate = item.publishedDate.orEmpty(),
            mainCategory = item.mainCategory.orEmpty(),
            averageRating = item.averageRating ?: 0f,
            imageLink = item.imageLinkSmall,
            language = item.language.orEmpty(),
            isFavorite = item.inFavorite,
            favoriteIcon = if (item.inFavorite) R.drawable.ic_bookmark_24
            else R.drawable.ic_bookmark_border_24
        )
}