package com.dev.miasnikoff.bookworm.core.network.model

import com.google.gson.annotations.SerializedName

data class Volume(
    @SerializedName("id") val id: String,
    @SerializedName("selfLink") val selfLink: String?,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo,
)
