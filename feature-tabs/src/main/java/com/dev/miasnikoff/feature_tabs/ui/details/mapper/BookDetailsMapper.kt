package com.dev.miasnikoff.feature_tabs.ui.details.mapper

import com.dev.miasnikoff.feature_tabs.data.local.BookEntity
import com.dev.miasnikoff.feature_tabs.data.remote.model.BookDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageLinksDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageSize
import com.dev.miasnikoff.feature_tabs.domain.model.BookDetails
import javax.inject.Inject

class BookDetailsMapper @Inject constructor() {

    fun fromDto(dto: BookDTO): BookDetails =
        BookDetails(
            id = dto.id,
            title = dto.bookInfo.title,
            subtitle = dto.bookInfo.subtitle.orEmpty(),
            authors = dto.bookInfo.authors?.joinToString().orEmpty(),
            publisher = dto.bookInfo.publisher.orEmpty(),
            publishedDate = dto.bookInfo.publishedDate.orEmpty(),
            categories = dto.bookInfo.categories?.joinToString()
                ?: dto.bookInfo.mainCategory.orEmpty(),
            averageRating = dto.bookInfo.averageRating.toFloat(),
            ratingsCount = dto.bookInfo.ratingsCount,
            description = dto.bookInfo.description.orEmpty(),
            pageCount = dto.bookInfo.pageCount ?: 0,
            printType = dto.bookInfo.printType.orEmpty(),
            imageLinkSmall = getImageOfSize(dto.bookInfo.imageLinks, ImageSize.S),
            imageLinkLarge = getImageOfSize(dto.bookInfo.imageLinks, ImageSize.M),
            language = dto.bookInfo.language.orEmpty(),
            previewLink = formatUrl(dto.bookInfo.previewLink.orEmpty()),
            infoLink = formatUrl(dto.bookInfo.infoLink.orEmpty()),
            canonicalLink = formatUrl(dto.bookInfo.canonicalVolumeLink.orEmpty()),
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
            averageRating = entity.averageRating,
            ratingsCount = entity.ratingsCount,
            description = entity.description.orEmpty(),
            pageCount = entity.pageCount ?: 0,
            printType = entity.printType.orEmpty(),
            imageLinkSmall = entity.imageLinkSmall,
            imageLinkLarge = entity.imageLinkLarge,
            language = entity.language.orEmpty(),
            previewLink = formatUrl(entity.previewLink.orEmpty()),
            infoLink = formatUrl(entity.infoLink.orEmpty()),
            canonicalLink = formatUrl(entity.canonicalVolumeLink.orEmpty()),
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
        return imgUrl?.let { formatUrl(it) }
    }

    private fun formatUrl(sourceUrl: String): String {
        if (sourceUrl.startsWith("http:")) {
            return sourceUrl.replaceFirst("http:", "https:")
        }
        return sourceUrl
    }
}