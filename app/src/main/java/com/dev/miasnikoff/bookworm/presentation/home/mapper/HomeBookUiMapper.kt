package com.dev.miasnikoff.bookworm.presentation.home.mapper

import com.dev.miasnikoff.bookworm.data.model.ImageSize
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.presentation._core.mapper.BaseUiDataMapper
import com.dev.miasnikoff.bookworm.presentation.home.model.HomeBookItem

class HomeBookUiMapper: BaseUiDataMapper<VolumeDTO>() {

    override fun toItem(itemDTO: VolumeDTO, imageSize: ImageSize): RecyclerItem =
        HomeBookItem(
            id = itemDTO.id,
            title = itemDTO.volumeInfo.title.orEmpty(),
            authors = itemDTO.volumeInfo.authors?.joinToString().orEmpty(),
            publisher = itemDTO.volumeInfo.publisher.orEmpty(),
            publishedDate = itemDTO.volumeInfo.publishedDate.orEmpty(),
            averageRating = itemDTO.volumeInfo.averageRating?.toFloat() ?: 0f,
            imageLink = getImageOfSize(itemDTO.volumeInfo.imageLinks, imageSize)
        )
}