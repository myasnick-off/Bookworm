package com.dev.miasnikoff.bookworm.presentation.home.mapper

import com.dev.miasnikoff.bookworm.data.model.ImageLinksDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.presentation.home.model.HomeBookItem

class HomeBookMapper {

    operator fun invoke(volumeDTO: VolumeDTO): HomeBookItem =
        HomeBookItem(
            id = volumeDTO.id,
            title = volumeDTO.volumeInfo.title.orEmpty(),
            authors = volumeDTO.volumeInfo.authors?.joinToString().orEmpty(),
            publisher = volumeDTO.volumeInfo.publisher.orEmpty(),
            publishedDate = volumeDTO.volumeInfo.publishedDate.orEmpty(),
            averageRating = volumeDTO.volumeInfo.averageRating?.toFloat() ?: 0f,
            imageLink = getImageLink(volumeDTO.volumeInfo.imageLinks),
        )

    private fun getImageLink(imgLinks: ImageLinksDTO?): String? {
        val imgUrl = imgLinks?.let {
            it.large ?: it.medium ?: it.small
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