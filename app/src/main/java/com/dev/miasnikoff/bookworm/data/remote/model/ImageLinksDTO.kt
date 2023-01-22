package com.dev.miasnikoff.bookworm.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageLinksDTO(
    @SerialName("smallThumbnail") val smallThumbnail: String? = null,
    @SerialName("thumbnail") val thumbnail: String? = null,
    @SerialName("small") val small: String? = null,
    @SerialName("medium") val medium: String? = null,
    @SerialName("large") val large: String? = null,
    @SerialName("extraLarge") val extraLarge: String? = null
)

enum class ImageSize {
    XXS,
    XS,
    S,
    M,
    L,
    XL;
}
