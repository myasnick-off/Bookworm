package com.dev.miasnikoff.bookworm.presentation.details.mapper

import com.dev.miasnikoff.bookworm.data.model.ImageLinksDTO
import com.dev.miasnikoff.bookworm.data.model.VolumeDTO
import com.dev.miasnikoff.bookworm.presentation.details.model.Volume

class VolumeDetailsMapper {

    operator fun invoke(volumeDTO: VolumeDTO): Volume =
        Volume(
            id = volumeDTO.id,
            title = volumeDTO.volumeInfo.title.orEmpty(),
            subtitle = volumeDTO.volumeInfo.subtitle.orEmpty(),
            authors = volumeDTO.volumeInfo.authors?.joinToString().orEmpty(),
            publisher = volumeDTO.volumeInfo.publisher.orEmpty(),
            publishedDate = volumeDTO.volumeInfo.publishedDate.orEmpty(),
            categories = volumeDTO.volumeInfo.categories?.joinToString()
                ?: volumeDTO.volumeInfo.mainCategory.orEmpty(),
            averageRating = volumeDTO.volumeInfo.averageRating?.toFloat() ?: 0f,
            ratingsCount = volumeDTO.volumeInfo.ratingsCount ?: 0,
            description = volumeDTO.volumeInfo.description.orEmpty(),
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