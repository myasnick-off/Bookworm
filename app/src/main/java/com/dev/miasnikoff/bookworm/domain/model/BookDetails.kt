package com.dev.miasnikoff.bookworm.domain.model

data class BookDetails(
    val id: String,
    val title: String,
    val subtitle: String,
    val authors: String,
    val publisher: String,
    val publishedDate: String,
    val categories: String,
    val averageRating: Float,
    val ratingsCount: Int,
    val description: String,
    val pageCount: Int,
    val printType: String,
    val imageLinkSmall: String?,
    val imageLinkLarge: String?,
    val language: String,
    val previewLink: String,
    val infoLink: String,
    val canonicalLink: String,
    val isHistory: Boolean = false,
    val isFavorite: Boolean = false
)
