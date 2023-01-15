package com.dev.miasnikoff.bookworm.presentation.list.mapper

import com.dev.miasnikoff.bookworm.data.model.ImageSize
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.presentation._core.mapper.BaseUiDataMapper
import com.dev.miasnikoff.bookworm.presentation.list.model.BookItem

class BookUiDataMapper : BaseUiDataMapper<VolumeDTO>() {

    override fun toItem(itemDTO: VolumeDTO, imageSize: ImageSize): RecyclerItem =
        BookItem(
            id = itemDTO.id,
            title = itemDTO.volumeInfo.title.orEmpty(),
            authors = itemDTO.volumeInfo.authors?.joinToString().orEmpty(),
            publishedDate = itemDTO.volumeInfo.publishedDate.orEmpty(),
            mainCategory = itemDTO.volumeInfo.mainCategory.orEmpty(),
            averageRating = itemDTO.volumeInfo.averageRating?.toFloat() ?: 0f,
            imageLink = getImageOfSize(itemDTO.volumeInfo.imageLinks, imageSize),
            language = itemDTO.volumeInfo.language.orEmpty()
        )
}