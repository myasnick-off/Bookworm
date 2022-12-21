package com.dev.miasnikoff.bookworm.list.mapper

import com.dev.miasnikoff.bookworm.core.network.model.ImageLinks
import com.dev.miasnikoff.bookworm.core.network.model.Volume
import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.list.adapter.VolumeItem

class VolumeDataMapper {
    fun toRecyclerItems(volumes: List<Volume>): List<RecyclerItem> =
        volumes.map { toItem(it) }

    private fun toItem(volume: Volume): RecyclerItem =
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

    private fun getSmallImage(imgLinks: ImageLinks?): String? {
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