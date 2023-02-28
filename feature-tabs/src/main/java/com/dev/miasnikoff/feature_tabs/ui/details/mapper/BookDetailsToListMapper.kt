package com.dev.miasnikoff.feature_tabs.ui.details.mapper

import com.dev.miasnikoff.core.extensions.customDateFormat
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.domain.model.BookDetails
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls.BookControlsItem
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.main.DetailsMainItem
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithheader.TextWithHeaderItem
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithlabel.TextWithLabelItem
import javax.inject.Inject

class BookDetailsToListMapper @Inject constructor() {

    fun toList(details: BookDetails): List<RecyclerItem> {
        val detailsList = mutableListOf<RecyclerItem>(
            DetailsMainItem(
                id = details.id,
                title = details.title,
                authors = details.authors,
                publisher = details.publisher,
                averageRating = details.averageRating,
                averageRatingTxt = String.format(FLOAT_FORMAT, details.averageRating),
                ratingsCount = details.ratingsCount,
                imageLink = details.imageLinkLarge,
                hasRating = details.ratingsCount> 0
            )
        )
        val languageItem = if (details.language.isEmpty()) null else TextWithLabelItem(
            id = "$LANG_ID_PREFIX${details.id}",
            labelRes = com.dev.miasnikoff.core_ui.R.string.language,
            text = details.language.uppercase()
        )
        val pagesItem = if (details.pageCount == 0) null else TextWithLabelItem(
            id = "$PAGES_ID_PREFIX${details.id}",
            labelRes = com.dev.miasnikoff.core_ui.R.string.pages_count,
            text = details.pageCount.toString()
        )
        val publishDateItem = if (details.publishedDate.isEmpty()) null else TextWithLabelItem(
            id = "$PUBLISH_DATE_ID_PREFIX${details.id}",
            labelRes = com.dev.miasnikoff.core_ui.R.string.published_date,
            text = details.publishedDate.customDateFormat(DATE_FORMAT),
        )
        val controlsItem = BookControlsItem(
            id = "$CTRL_ID_PREFIX${details.id}",
            favoriteIcon =
            if (details.isFavorite) com.dev.miasnikoff.core_ui.R.drawable.ic_bookmark_36
            else com.dev.miasnikoff.core_ui.R.drawable.ic_bookmark_border_36,
            previewLink = details.previewLink
        )
        val categoryItem = if (details.categories.isEmpty()) null else TextWithHeaderItem(
            id = "$CATEGORY_ID_PREFIX${details.id}",
            headerRes = com.dev.miasnikoff.core_ui.R.string.categories,
            text = details.categories
        )
        val aboutItem = if (details.description.isEmpty()) null else TextWithHeaderItem(
            id = "$ABOUT_ID_PREFIX${details.id}",
            headerRes = com.dev.miasnikoff.core_ui.R.string.about,
            text = details.description
        )
        detailsList.addAll(
            listOfNotNull(
                languageItem,
                pagesItem,
                publishDateItem,
                controlsItem,
                categoryItem,
                aboutItem
            )
        )

        return detailsList
    }

    companion object {
        private const val FLOAT_FORMAT = "%.2g"
        private const val DATE_FORMAT = "dd MMMM yyyy"
        private const val LANG_ID_PREFIX = "lang_"
        private const val PAGES_ID_PREFIX = "pages_"
        private const val PUBLISH_DATE_ID_PREFIX = "publish_date_"
        private const val CTRL_ID_PREFIX = "ctrl_"
        private const val CATEGORY_ID_PREFIX = "category_"
        private const val ABOUT_ID_PREFIX = "about_"
    }
}