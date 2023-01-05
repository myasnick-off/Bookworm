package com.dev.miasnikoff.bookworm.presentation.list.mapper

import com.dev.miasnikoff.bookworm.data.model.ImageLinksDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.presentation.list.model.VolumeItem

class VolumeDataMapper {
    fun toRecyclerItems(volumes: List<VolumeDTO>): List<RecyclerItem> =
        volumes.map { toItem(it) }

    private fun toItem(volume: VolumeDTO): RecyclerItem =
        VolumeItem(
            id = volume.id,
            title = volume.volumeInfo.title.orEmpty(),
            authors = volume.volumeInfo.authors?.joinToString().orEmpty(),
            publishedDate = volume.volumeInfo.publishedDate.orEmpty(),
            mainCategory = volume.volumeInfo.mainCategory.orEmpty(),
            averageRating = volume.volumeInfo.averageRating?.toFloat() ?: 0f,
            imageLink = getSmallImage(volume.volumeInfo.imageLinks),
            language = volume.volumeInfo.language.orEmpty()
        )

    private fun getSmallImage(imgLinks: ImageLinksDTO?): String? {
        val imgUrl = imgLinks?.let {
            it.small ?: it.thumbnail ?: it.smallThumbnail
        }
        return imgUrl?.let { formatImageUrl(it) }
    }

    private fun formatImageUrl(sourceUrl: String): String {
        if (sourceUrl.startsWith("http:")) {
            return sourceUrl.replaceFirst("http:", "https:")
        }
        return sourceUrl
    }
}