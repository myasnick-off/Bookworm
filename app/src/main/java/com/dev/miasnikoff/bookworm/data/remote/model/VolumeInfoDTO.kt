package com.dev.miasnikoff.bookworm.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfoDTO(
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String? = null,
    @SerialName("authors") val authors: List<String>? = null,
    @SerialName("publisher") val publisher: String? = null,
    @SerialName("publishedDate") val publishedDate: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("pageCount") val pageCount: Int? = null,
    @SerialName("printType") val printType: String? = null,
    @SerialName("mainCategory") val mainCategory: String? = null,
    @SerialName("categories") val categories: List<String>? = null,
    @SerialName("averageRating") val averageRating: Double? = null,
    @SerialName("ratingsCount") val ratingsCount: Int? = null,
    @SerialName("contentVersion") val contentVersion: String? = null,
    @SerialName("imageLinks") val imageLinks: ImageLinksDTO? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("previewLink") val previewLink: String? = null,
    @SerialName("infoLink") val infoLink: String? = null,
    @SerialName("canonicalVolumeLink") val canonicalVolumeLink: String? = null
)
