package com.dev.miasnikoff.bookworm.data.model

import com.google.gson.annotations.SerializedName

data class VolumeDTO(
    @SerializedName("id") val id: String,
    @SerializedName("selfLink") val selfLink: String?,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfoDTO,
)
