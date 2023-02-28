package com.dev.miasnikoff.feature_tabs.ui.base

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageLinksDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageSize

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

    companion object {
        const val FLOAT_FORMAT = "%.2g"
        const val DATE_FORMAT = "dd MMMM yyyy"
    }
}