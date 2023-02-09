package com.dev.miasnikoff.feature_tabs.ui.home.mapper

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageSize
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeDTO
import com.dev.miasnikoff.feature_tabs.ui.base.BaseUiDataMapper
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.bookofday.BookOfDayItem
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.litebook.LiteBookItem
import javax.inject.Inject

class HomeDtoToUiMapper @Inject constructor(): BaseUiDataMapper<VolumeDTO>() {

    override fun toItem(item: VolumeDTO, imageSize: ImageSize): RecyclerItem =
        LiteBookItem(
            id = item.id,
            title = item.volumeInfo.title.orEmpty(),
            authors = item.volumeInfo.authors?.joinToString().orEmpty(),
            averageRating = item.volumeInfo.averageRating?.toFloat() ?: 0f,
            imageLink = getImageOfSize(item.volumeInfo.imageLinks, imageSize)
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