package com.dev.miasnikoff.feature_tabs.ui.home.mapper

import com.dev.miasnikoff.core.extensions.customDateFormat
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.data.local.BookEntity
import com.dev.miasnikoff.feature_tabs.data.remote.model.ImageSize
import com.dev.miasnikoff.feature_tabs.ui.base.BaseUiDataMapper
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.bookofday.BookOfDayItem
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.litebook.LiteBookItem
import javax.inject.Inject

class HomeEntityToUiMapper @Inject constructor(): BaseUiDataMapper<BookEntity>() {

    override fun toItem(item: BookEntity, imageSize: ImageSize): RecyclerItem =
        LiteBookItem(
            id = item.id,
            title = item.title.orEmpty(),
            authors = item.authors.orEmpty(),
            imageLink = item.imageLinkSmall
        )

    fun toSingleItem(item: BookEntity?): RecyclerItem? =
        item?.let {
            BookOfDayItem(
                id = item.id,
                title = item.title.orEmpty(),
                authors = item.authors.orEmpty(),
                publisher = item.publisher.orEmpty(),
                publishedDate = item.publishedDate.orEmpty().customDateFormat(DATE_FORMAT),
                averageRating = item.averageRating,
                averageRatingTxt = String.format(FLOAT_FORMAT, item.averageRating),
                ratingsCount = item.ratingsCount,
                imageLink = item.imageLinkLarge,
                hasRating = item.ratingsCount > 0
            )
        }
}