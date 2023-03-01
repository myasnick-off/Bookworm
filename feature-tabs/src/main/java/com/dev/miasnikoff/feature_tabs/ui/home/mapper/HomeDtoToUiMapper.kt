package com.dev.miasnikoff.feature_tabs.ui.home.mapper

import com.dev.miasnikoff.core.extensions.customDateFormat
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.data.remote.model.BookDTO
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageSize
import com.dev.miasnikoff.feature_tabs.ui.base.BaseUiDataMapper
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.bookofday.BookOfDayItem
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.litebook.LiteBookItem
import javax.inject.Inject

class HomeDtoToUiMapper @Inject constructor() : BaseUiDataMapper<BookDTO>() {

    override fun toItem(item: BookDTO, imageSize: ImageSize): RecyclerItem =
        LiteBookItem(
            id = item.id,
            title = item.bookInfo.title,
            authors = item.bookInfo.authors?.joinToString().orEmpty(),
            imageLink = getImageOfSize(item.bookInfo.imageLinks, imageSize)
        )

    fun toSingleItem(itemDTO: BookDTO?, imageSize: ImageSize): RecyclerItem? =
        itemDTO?.let {
            BookOfDayItem(
                id = itemDTO.id,
                title = itemDTO.bookInfo.title,
                authors = itemDTO.bookInfo.authors?.joinToString().orEmpty(),
                publisher = itemDTO.bookInfo.publisher.orEmpty(),
                publishedDate =
                itemDTO.bookInfo.publishedDate.orEmpty().customDateFormat(DATE_FORMAT),
                averageRating = itemDTO.bookInfo.averageRating.toFloat(),
                averageRatingTxt = String.format(FLOAT_FORMAT, itemDTO.bookInfo.averageRating),
                ratingsCount = itemDTO.bookInfo.ratingsCount,
                imageLink = getImageOfSize(itemDTO.bookInfo.imageLinks, imageSize),
                hasRating = itemDTO.bookInfo.ratingsCount > 0
            )
        }
}