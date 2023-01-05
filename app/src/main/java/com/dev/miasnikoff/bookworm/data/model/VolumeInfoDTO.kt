package com.dev.miasnikoff.bookworm.data.model

import com.google.gson.annotations.SerializedName

data class VolumeInfoDTO(
    @SerializedName("title") val title: String?,
    @SerializedName("subtitle") val subtitle: String?,
    @SerializedName("authors") val authors: List<String>?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("publishedDate") val publishedDate: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("pageCount") val pageCount: Int?,
    @SerializedName("printType") val printType: String?,
    @SerializedName("mainCategory") val mainCategory: String?,
    @SerializedName("categories") val categories: List<String>?,
    @SerializedName("averageRating") val averageRating: Double?,
    @SerializedName("ratingsCount") val ratingsCount: Int?,
    @SerializedName("contentVersion") val contentVersion: String?,
    @SerializedName("imageLinks") val imageLinks: ImageLinksDTO?,
    @SerializedName("language") val language: String?,
    @SerializedName("previewLink") val previewLink: String?,
    @SerializedName("infoLink") val infoLink: String?,
    @SerializedName("canonicalVolumeLink") val canonicalVolumeLink: String?
)
