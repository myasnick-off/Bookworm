package com.dev.miasnikoff.bookworm.ui._core.mapper

import com.dev.miasnikoff.bookworm.data.model.ImageLinksDTO
import com.dev.miasnikoff.bookworm.data.model.ImageSize
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

abstract class BaseUiDataMapper<T> {
    fun toItemList(volumes: List<T>): List<RecyclerItem> =
        volumes.map { toItem(it, ImageSize.S) }

    abstract fun toItem(item: T, imageSize: ImageSize): RecyclerItem

    protected fun getImageOfSize(imgLinks: ImageLinksDTO?, imageSize: ImageSize): String? {
        val imgUrl = imgLinks?.let {
            when (imageSize) {
                ImageSize.XXS -> it.smallThumbnail
                ImageSize.XS -> it.thumbnail ?: it.smallThumbnail
                ImageSize.S -> it.small ?: it.thumbnail ?: it.smallThumbnail
                ImageSize.M -> it.medium ?: it.small ?: it.thumbnail
                ImageSize.L -> it.large ?: it.medium ?: it.small
                ImageSize.XL -> it.extraLarge ?: it.large ?: it.medium
            }
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