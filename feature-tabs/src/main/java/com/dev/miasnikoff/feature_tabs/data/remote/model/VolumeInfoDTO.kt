package com.dev.miasnikoff.feature_tabs.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

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
    @SerialName("averageRating") val averageRating: Double = generateRandomRating(),
    @SerialName("ratingsCount") val ratingsCount: Int = generateRandomRatingsCount(averageRating),
    @SerialName("contentVersion") val contentVersion: String? = null,
    @SerialName("imageLinks") val imageLinks: ImageLinksDTO? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("previewLink") val previewLink: String? = null,
    @SerialName("infoLink") val infoLink: String? = null,
    @SerialName("canonicalVolumeLink") val canonicalVolumeLink: String? = null
) {
    companion object {
        private fun generateRandomRating(): Double =
            if (Random.nextInt(0, 5) > 0) Random.nextDouble(1.0, 5.0) else 0.0

        private fun generateRandomRatingsCount(rating: Double): Int =
            if (rating > 0) Random.nextInt(1, 99) else 0
    }
}
