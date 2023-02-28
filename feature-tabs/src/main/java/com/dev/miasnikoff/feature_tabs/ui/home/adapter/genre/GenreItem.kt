package com.dev.miasnikoff.feature_tabs.ui.home.adapter.genre

import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.feature_tabs.R

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
    SCIENCE(query = "subject:science", titleRes = com.dev.miasnikoff.core_ui.R.string.science, R.drawable.science),
    EDUCATION(query = "subject:education", titleRes = com.dev.miasnikoff.core_ui.R.string.education, R.drawable.education),
    DESIGN(query = "subject:design", titleRes = com.dev.miasnikoff.core_ui.R.string.design, R.drawable.craft),
    ROMANCE(query = "subject:drama/general", titleRes = com.dev.miasnikoff.core_ui.R.string.romance, R.drawable.romance),
    FICTION(query = "subject:fiction", titleRes = com.dev.miasnikoff.core_ui.R.string.fiction, R.drawable.fiction),
    HUMOR(query = "subject:humor", titleRes = com.dev.miasnikoff.core_ui.R.string.humor, R.drawable.humor),
    POETRY(query = "subject:poetry", titleRes = com.dev.miasnikoff.core_ui.R.string.poetry, R.drawable.poetry);

}
