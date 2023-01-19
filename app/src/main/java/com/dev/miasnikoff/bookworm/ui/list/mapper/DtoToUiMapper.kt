package com.dev.miasnikoff.bookworm.ui.list.mapper

import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.data.model.ImageSize
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui._core.mapper.BaseUiDataMapper
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookItem

class DtoToUiMapper : BaseUiDataMapper<VolumeDTO>() {

    override fun toItem(item: VolumeDTO, imageSize: ImageSize): RecyclerItem =
        BookItem(
            id = item.id,
            title = item.volumeInfo.title.orEmpty(),
            authors = item.volumeInfo.authors?.joinToString().orEmpty(),
            publishedDate = item.volumeInfo.publishedDate.orEmpty(),
            mainCategory = item.volumeInfo.mainCategory.orEmpty(),
            averageRating = item.volumeInfo.averageRating?.toFloat() ?: 0f,
            imageLink = getImageOfSize(item.volumeInfo.imageLinks, imageSize),
            language = item.volumeInfo.language.orEmpty(),
            isFavorite = item.isFavorite,
            favoriteIcon = if (item.isFavorite) R.drawable.ic_bookmark_24
            else R.drawable.ic_bookmark_border_24
        )
}