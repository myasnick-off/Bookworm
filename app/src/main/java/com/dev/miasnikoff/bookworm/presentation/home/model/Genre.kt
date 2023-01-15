package com.dev.miasnikoff.bookworm.presentation.home.model

import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem

data class Genre(
    override val id: String,
    val genreData: GenreData,
) : RecyclerItem {

    companion object {
        fun createList(): List<Genre> {
            val result = mutableListOf<Genre>()
            GenreData.values().forEach { genreData ->
                result.add(Genre(id = genreData.name, genreData = genreData))
            }
            return result.toList()
        }
    }
}

enum class GenreData(val query: String, val titleRes: Int, val imgResId: Int) {
    ROMANCE(query = "+subject:drama/general", titleRes = R.string.romance, 0),
    FICTION(query = "+subject:fiction", titleRes = R.string.fiction, 0),
    HUMOR(query = "+subject:humor", titleRes = R.string.humor, 0),
    POETRY(query = "+subject:poetry", titleRes = R.string.poetry, 0),
    EDUCATION(query = "+subject:education", titleRes = R.string.education, 0),
    SCIENCE(query = "+subject:science", titleRes = R.string.science, 0),
    DESIGN(query = "+subject:design", titleRes = R.string.design, 0);

}
