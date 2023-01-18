package com.dev.miasnikoff.bookworm.ui.home.adapter.genre

import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem

data class GenreItem(
    override val id: String,
    val genreData: GenreData,
) : RecyclerItem {

    companion object {
        fun createList(): List<RecyclerItem> {
            val result = mutableListOf<GenreItem>()
            GenreData.values().forEach { genreData ->
                result.add(GenreItem(id = genreData.name, genreData = genreData))
            }
            return result.toList()
        }
    }
}

enum class GenreData(val query: String, val titleRes: Int, val imgResId: Int) {
    SCIENCE(query = "subject:science", titleRes = R.string.science, R.drawable.science),
    EDUCATION(query = "subject:education", titleRes = R.string.education, R.drawable.education),
    DESIGN(query = "subject:design", titleRes = R.string.design, R.drawable.craft),
    ROMANCE(query = "subject:drama/general", titleRes = R.string.romance, R.drawable.romance),
    FICTION(query = "subject:fiction", titleRes = R.string.fiction, R.drawable.fiction),
    HUMOR(query = "subject:humor", titleRes = R.string.humor, R.drawable.humor),
    POETRY(query = "subject:poetry", titleRes = R.string.poetry, R.drawable.poetry);

}
