package com.dev.miasnikoff.feature_tabs.ui.list.mapper

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.data.local.BookEntity
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageSize
import com.dev.miasnikoff.feature_tabs.ui.base.BaseUiDataMapper
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
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
            favoriteIcon = if (item.inFavorite) com.dev.miasnikoff.core_ui.R.drawable.ic_bookmark_24
            else com.dev.miasnikoff.core_ui.R.drawable.ic_bookmark_border_24
        )
}