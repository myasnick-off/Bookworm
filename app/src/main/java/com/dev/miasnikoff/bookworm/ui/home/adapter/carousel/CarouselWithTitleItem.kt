package com.dev.miasnikoff.bookworm.ui.home.adapter.carousel

import android.os.Parcelable
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui.home.adapter.genre.GenreItem
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
    POP_GENRES(titleRes = R.string.popular_genres),
    LAST_VIEWED(titleRes = R.string.last_viewed),
    NEWEST(titleRes = R.string.newest),
    FREE(titleRes = R.string.free_books);

}