package com.dev.miasnikoff.bookworm.ui.details.mapper

import com.dev.miasnikoff.bookworm.data.local.model.BookEntity
import com.dev.miasnikoff.bookworm.data.remote.model.ImageLinksDTO
import com.dev.miasnikoff.bookworm.data.remote.model.ImageSize
import com.dev.miasnikoff.bookworm.data.remote.model.VolumeDTO
import com.dev.miasnikoff.bookworm.domain.model.BookDetails
import javax.inject.Inject

class BookDetailsMapper @Inject constructor() {

    fun fromDto(dto: VolumeDTO): BookDetails =
        BookDetails(
            id = dto.id,
            title = dto.volumeInfo.title.orEmpty(),
            subtitle = dto.volumeInfo.subtitle.orEmpty(),
            authors = dto.volumeInfo.authors?.joinToString().orEmpty(),
            publisher = dto.volumeInfo.publisher.orEmpty(),
            publishedDate = dto.volumeInfo.publishedDate.orEmpty(),
            categories = dto.volumeInfo.categories?.joinToString()
                ?: dto.volumeInfo.mainCategory.orEmpty(),
            averageRating = dto.volumeInfo.averageRating?.toFloat() ?: 0f,
            ratingsCount = dto.volumeInfo.ratingsCount ?: 0,
            description = dto.volumeInfo.description.orEmpty(),
            pageCount = dto.volumeInfo.pageCount ?: 0,
            printType = dto.volumeInfo.printType.orEmpty(),
            imageLinkSmall = getImageOfSize(dto.volumeInfo.imageLinks, ImageSize.S),
            imageLinkLarge = getImageOfSize(dto.volumeInfo.imageLinks, ImageSize.L),
            language = dto.volumeInfo.language.orEmpty(),
            previewLink = dto.volumeInfo.previewLink.orEmpty(),
            infoLink = dto.volumeInfo.infoLink.orEmpty(),
            canonicalLink = dto.volumeInfo.canonicalVolumeLink.orEmpty(),
            isFavorite = dto.isFavorite
        )

    fun fromEntity(entity: BookEntity): BookDetails =
        BookDetails(
            id = entity.id,
            title = entity.title.orEmpty(),
            subtitle = entity.subtitle.orEmpty(),
            authors = entity.authors.orEmpty(),
            publisher = entity.publisher.orEmpty(),
            publishedDate = entity.publishedDate.orEmpty(),
            categories = entity.categories ?: entity.mainCategory.orEmpty(),
            averageRating = entity.averageRating ?: 0f,
            ratingsCount = entity.ratingsCount ?: 0,
            description = entity.description.orEmpty(),
            pageCount = entity.pageCount ?: 0,
            printType = entity.printType.orEmpty(),
            imageLinkSmall = entity.imageLinkSmall,
            imageLinkLarge = entity.imageLinkLarge,
            language = entity.language.orEmpty(),
            previewLink = entity.previewLink.orEmpty(),
            infoLink = entity.infoLink.orEmpty(),
            canonicalLink = entity.canonicalVolumeLink.orEmpty(),
            isHistory = entity.inHistory,
            isFavorite = entity.inFavorite
        )

    fun toEntity(details: BookDetails): BookEntity =
        BookEntity(
            id = details.id,
            title = details.title,
            subtitle = details.subtitle,
            authors = details.authors,
            publisher = details.publisher,
            publishedDate = details.publishedDate,
            description = details.description,
            pageCount = details.pageCount,
            printType = details.printType,
            mainCategory = null,
            categories = details.categories,
            averageRating = details.averageRating,
            ratingsCount = details.ratingsCount,
            imageLinkSmall = details.imageLinkSmall,
            imageLinkLarge = details.imageLinkLarge,
            language = details.language,
            previewLink = details.previewLink,
            infoLink = details.infoLink,
            canonicalVolumeLink = details.canonicalLink,
            inHistory = true,
            inFavorite = details.isFavorite
        )

    private fun getImageOfSize(imgLinks: ImageLinksDTO?, imageSize: ImageSize): String? {
        val imgUrl = imgLinks?.let {
            when (imageSize) {
                ImageSize.XXS -> it.smallThumbnail
                ImageSize.XS -> it.thumbnail ?: it.smallThumbnail
                ImageSize.S -> it.small ?: it.thumbnail ?: it.smallThumbnail
                ImageSize.M -> it.medium ?: it.small ?: it.thumbnail
                ImageSize.L -> it.large ?: it.medium ?: it.small
                ImageSize.XL -> it.extraLarge ?: it.large ?: it.medium
            }
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