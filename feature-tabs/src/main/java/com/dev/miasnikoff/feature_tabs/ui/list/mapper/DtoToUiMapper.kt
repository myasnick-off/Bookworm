package com.dev.miasnikoff.feature_tabs.ui.list.mapper

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageSize
import com.dev.miasnikoff.feature_tabs.data.remote.model.VolumeDTO
import com.dev.miasnikoff.feature_tabs.ui.base.BaseUiDataMapper
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import javax.inject.Inject

class DtoToUiMapper @Inject constructor() : BaseUiDataMapper<VolumeDTO>() {

    override fun toItem(item: VolumeDTO, imageSize: ImageSize): RecyclerItem =
        BookItem(
            id = item.id,
            title = item.volumeInfo.title,
            authors = item.volumeInfo.authors?.joinToString().orEmpty(),
            publishedDate = item.volumeInfo.publishedDate.orEmpty(),
            mainCategory = item.volumeInfo.mainCategory.orEmpty(),
            averageRating = item.volumeInfo.averageRating?.toFloat() ?: 0f,
            imageLink = getImageOfSize(item.volumeInfo.imageLinks, imageSize),
            language = item.volumeInfo.language.orEmpty(),
            isFavorite = item.isFavorite,
            favoriteIcon = if (item.isFavorite) com.dev.miasnikoff.core_ui.R.drawable.ic_bookmark_24
            else com.dev.miasnikoff.core_ui.R.drawable.ic_bookmark_border_24
        )
}