package com.dev.miasnikoff.bookworm.presentation.details.model

data class Volume(
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
    val imageLink: String?,
    val language: String,
    val isFavorite: Boolean = false
)
