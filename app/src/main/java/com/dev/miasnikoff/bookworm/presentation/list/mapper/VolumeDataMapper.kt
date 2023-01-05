package com.dev.miasnikoff.bookworm.presentation.list.mapper

import com.dev.miasnikoff.bookworm.data.model.ImageLinksDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.presentation.list.model.VolumeItem

class VolumeDataMapper {
    fun toRecyclerItems(volumes: List<VolumeDTO>): List<RecyclerItem> =
        volumes.map { toItem(it) }

    private fun toItem(volumeDTO: VolumeDTO): RecyclerItem =
        VolumeItem(
            id = volumeDTO.id,
            title = volumeDTO.volumeInfo.title.orEmpty(),
            authors = volumeDTO.volumeInfo.authors?.toString()?.trim('[', ']').orEmpty(),
            publishedDate = volumeDTO.volumeInfo.publishedDate.orEmpty(),
            mainCategory = volumeDTO.volumeInfo.mainCategory.orEmpty(),
            averageRating = volumeDTO.volumeInfo.averageRating?.toFloat() ?: 0f,
            imageLink = getSmallImage(volumeDTO.volumeInfo.imageLinks),
            language = volumeDTO.volumeInfo.language.orEmpty()
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