package com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel

import android.os.Parcelable
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.genre.GenreItem
import kotlinx.parcelize.Parcelize

data class CarouselWithTitleItem(
    override val id: String,
    val category: Category,
    val items: List<RecyclerItem>
) : RecyclerItem {

    companion object {
        fun createGenreCarousel(): CarouselWithTitleItem =
            CarouselWithTitleItem(
                id = Category.POP_GENRES.name,
                category = Category.POP_GENRES,
                items = GenreItem.createList()
            )

        fun createCarouselOfCategory(category: Category, items: List<RecyclerItem>) =
            if (items.isNotEmpty()) CarouselWithTitleItem(
                id = category.name,
                category = category,
                items = items
            )
            else null
    }
}

@Parcelize
enum class Category(val titleRes: Int) : Parcelable {
    POP_GENRES(titleRes = com.dev.miasnikoff.core_ui.R.string.popular_genres),
    LAST_VIEWED(titleRes = com.dev.miasnikoff.core_ui.R.string.last_viewed),
    NEWEST(titleRes = com.dev.miasnikoff.core_ui.R.string.newest),
    FREE(titleRes = com.dev.miasnikoff.core_ui.R.string.free_books),
    FAVORITE(titleRes = com.dev.miasnikoff.core_ui.R.string.favorite),
    NONE(titleRes = -1);
}