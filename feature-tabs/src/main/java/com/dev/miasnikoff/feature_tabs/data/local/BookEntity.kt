package com.dev.miasnikoff.feature_tabs.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "subtitle") val subtitle: String?,
    @ColumnInfo(name = "authors") val authors: String?,
    @ColumnInfo(name = "publisher") val publisher: String?,
    @ColumnInfo(name = "publishedDate") val publishedDate: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "pageCount") val pageCount: Int?,
    @ColumnInfo(name = "printType") val printType: String?,
    @ColumnInfo(name = "mainCategory") val mainCategory: String?,
    @ColumnInfo(name = "categories") val categories: String?,
    @ColumnInfo(name = "averageRating") val averageRating: Float?,
    @ColumnInfo(name = "ratingsCount") val ratingsCount: Int?,
    @ColumnInfo(name = "imageLinkSmall") val imageLinkSmall: String?,
    @ColumnInfo(name = "imageLinkLarge") val imageLinkLarge: String?,
    @ColumnInfo(name = "language") val language: String?,
    @ColumnInfo(name = "previewLink") val previewLink: String?,
    @ColumnInfo(name = "infoLink") val infoLink: String?,
    @ColumnInfo(name = "canonicalVolumeLink") val canonicalVolumeLink: String?,
    @ColumnInfo(name = "in_history") val inHistory: Boolean = false,
    @ColumnInfo(name = "in_favorite") val inFavorite: Boolean = false
)