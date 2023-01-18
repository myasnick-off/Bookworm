package com.dev.miasnikoff.bookworm.data.model

import com.google.gson.annotations.SerializedName

data class ImageLinksDTO(
    @SerializedName("smallThumbnail") val smallThumbnail: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("small") val small: String?,
    @SerializedName("medium") val medium: String?,
    @SerializedName("large") val large: String?,
    @SerializedName("extraLarge") val extraLarge: String?
)

enum class ImageSize {
    XXS,
    XS,
    S,
    M,
    L,
    XL;
}
