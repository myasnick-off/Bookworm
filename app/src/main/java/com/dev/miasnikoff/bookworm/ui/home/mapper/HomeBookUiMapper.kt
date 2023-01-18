package com.dev.miasnikoff.bookworm.ui.home.mapper

import com.dev.miasnikoff.bookworm.data.model.ImageSize
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui._core.mapper.BaseUiDataMapper
import com.dev.miasnikoff.bookworm.ui.home.adapter.bookofday.BookOfDayItem
import com.dev.miasnikoff.bookworm.ui.home.adapter.litebook.LiteBookItem

class HomeBookUiMapper: BaseUiDataMapper<VolumeDTO>() {

    override fun toItem(itemDTO: VolumeDTO, imageSize: ImageSize): RecyclerItem =
        LiteBookItem(
            id = itemDTO.id,
            title = itemDTO.volumeInfo.title.orEmpty(),
            authors = itemDTO.volumeInfo.authors?.joinToString().orEmpty(),
            averageRating = itemDTO.volumeInfo.averageRating?.toFloat() ?: 0f,
            imageLink = getImageOfSize(itemDTO.volumeInfo.imageLinks, imageSize)
        )

    fun toSingleItem(itemDTO: VolumeDTO?, imageSize: ImageSize): RecyclerItem? =
        itemDTO?.let {
            BookOfDayItem(
                id = itemDTO.id,
                title = itemDTO.volumeInfo.title.orEmpty(),
                authors = itemDTO.volumeInfo.authors?.joinToString().orEmpty(),
                publisher = itemDTO.volumeInfo.publisher.orEmpty(),
                publishedDate = itemDTO.volumeInfo.publishedDate.orEmpty(),
                averageRating = itemDTO.volumeInfo.averageRating?.toFloat() ?: 0f,
                imageLink = getImageOfSize(itemDTO.volumeInfo.imageLinks, imageSize)
            )
        }
}