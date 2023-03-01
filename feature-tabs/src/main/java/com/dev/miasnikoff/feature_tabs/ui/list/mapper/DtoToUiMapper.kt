package com.dev.miasnikoff.feature_tabs.ui.list.mapper

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.data.remote.model.BookDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageSize
import com.dev.miasnikoff.feature_tabs.ui.base.BaseUiDataMapper
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookItem
import javax.inject.Inject

class DtoToUiMapper @Inject constructor() : BaseUiDataMapper<BookDTO>() {

    override fun toItem(item: BookDTO, imageSize: ImageSize): RecyclerItem =
        BookItem(
            id = item.id,
            title = item.bookInfo.title,
            authors = item.bookInfo.authors?.joinToString().orEmpty(),
            publishedDate = item.bookInfo.publishedDate.orEmpty(),
            mainCategory = item.bookInfo.mainCategory.orEmpty(),
            averageRating = item.bookInfo.averageRating.toFloat(),
            averageRatingTxt = String.format(FLOAT_FORMAT, item.bookInfo.averageRating),
            imageLink = getImageOfSize(item.bookInfo.imageLinks, imageSize),
            language = item.bookInfo.language.orEmpty(),
            isFavorite = item.isFavorite,
            favoriteIcon =
            if (item.isFavorite) R.drawable.ic_bookmark_24
            else R.drawable.ic_bookmark_border_24
        )
}